package DataReaders;
import Helpers.DataPoint;
import Helpers.NotificationHandler;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class ReadTransactions {

    private enum Period {
        DAILY, MONTHLY, YEARLY
    } Period period;

    // api used by Coordinator charts
    public void setPeriodDaily() {
        period = Period.DAILY;
    }

    public void setPeriodMonthly() {
        period = Period.MONTHLY;
    }

    public void setPeriodYearly() {
        period = Period.YEARLY;
    }


    ArrayList<DataPoint> transactionData = new ArrayList<>();
    Map<String, Integer> map = new TreeMap<>();

    // reads data (x, y) in pairs for chart
    public ArrayList<DataPoint> data(String fileName, String selectedMonth, String selectedYear) {
        transactionData.clear();
        map.clear();
        String line;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            // read and process each line for selected period
            while ((line = reader.readLine()) != null) {

                String[] readData = line.split("-");
                if (readData.length < 4) continue;           // skips bad lines
                switch (period) {
                    case DAILY -> daily(readData, selectedMonth, selectedYear);
                    case MONTHLY -> monthly(readData, selectedYear);
                    case YEARLY -> yearly(readData);
                }
            }

            // convert map entries to array list pairs
            for (Map.Entry<String, Integer> entry : map.entrySet()) {

                int date = Integer.parseInt(entry.getKey());
                int amount = entry.getValue();
                transactionData.add(new DataPoint(date, amount));
            }
        } catch (IOException e) {
            NotificationHandler.getInstance().callNotificationHandler("Could not load data, try again.");
        }
        return transactionData;
    }


    // reads daily transactions (based on year & month picked by user)
    public void daily(String[] readData, String selectedMonth, String selectedYear) {

        String yearRead = readData[0].trim();
        String monthRead = readData[1].trim();
        String dateRead = readData[2].trim();
        String amountRead = readData[3].trim();

        // check if we are reading the same year / month user selected
        if (selectedYear.equals(yearRead) && selectedMonth.equals(monthRead)) {
            // if a dupe is found, sum up the amount
            int amount = Integer.parseInt(amountRead);
            map.put(dateRead, map.getOrDefault(dateRead, 0) + amount);
        }

    }

    // reads transactions monthly (based on year picked by user)
    public void monthly(String[] readData, String selectedYear) {

        String yearRead = readData[0].trim();
        String monthRead = readData[1].trim();
        String amountRead = readData[3].trim();

        // check if we are still reading data from same year the user selected
        if (yearRead.equals(selectedYear)) {
            int amount = Integer.parseInt(amountRead);
            // if a dupe is found, sum up the amount
            map.put(monthRead, map.getOrDefault(monthRead, 0) + amount);
        }
    }

    // reads transactions daily
    public void yearly(String[] readData) {
        String yearRead = readData[0].trim();
        String amountRead = readData[3].trim();

        // sum up dupes
        int amount = Integer.parseInt(amountRead);
        map.put(yearRead, map.getOrDefault(yearRead, 0) + amount);
    }
}





