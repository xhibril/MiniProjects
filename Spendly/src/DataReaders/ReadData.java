package DataReaders;
import Helpers.NotificationHandler;

import java.io.*;
import java.util.*;

public class ReadData {

    // read the months found in the file and year selected by user
   public ArrayList<String> storedMonths(String fileName, String selectedYear) {
        String line;
        ArrayList<String> monthsList = new ArrayList<>();

       // remove dupe keys and sorts from low -> high
       SortedSet<String> monthsSet = new TreeSet<>();

       try (BufferedReader reader = new BufferedReader(new FileReader(fileName))){

            while ((line = reader.readLine()) != null) {
                String[] readData = line.split("-");
                if (readData.length < 4) continue;           // skips bad lines

                String yearRead = readData[0].trim();          // year-month-date-amount
                String monthRead = readData[1].trim();

                if (selectedYear.equals(yearRead)) {         // only find months from requested years
                    monthsSet.add(monthRead); // remove dupes
                }
            }

            // take data from months set -> months array list
            for (String month : monthsSet){
                monthsList.add(month);
            }
        } catch (IOException e) {
            NotificationHandler.getInstance().callNotificationHandler("Could not load data, try again.");
        }
       return monthsList;
    }


    // read years found in file selected by user
   public ArrayList<String> storedYears(String fileName) {
        String line;
        ArrayList<String> yearsList = new ArrayList<>();
        SortedSet<String> yearSet = new TreeSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))){

            while ((line = reader.readLine()) != null) {
                String[] readData = line.split("-");
                if (readData.length < 4) continue;           // skips bad lines
                String yearRead = readData[0];
                yearSet.add(yearRead);
            }

            // take data from sorted set -> years array list
            for (String years : yearSet) {
                yearsList.add(years);
            }
        } catch (IOException e) {
            NotificationHandler.getInstance().callNotificationHandler("Could not load data, try again.");
        }
        return yearsList;
    }

    // turns raw months numbers in names
    public ArrayList<String> returnedMonths(String fileName, String year) {
        ArrayList<String> numericMonths = storedMonths(fileName, year);

        String[] monthNames = {
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        };
        ArrayList<String> namedMonths = new ArrayList<>();
        for (String m : numericMonths) {
            int index = Integer.parseInt(m) - 1;
            if (index >= 0 && index < 12) namedMonths.add(monthNames[index]);
        }
        return namedMonths;
    }
}


