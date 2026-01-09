package Helpers;
import DataReaders.ReadData;
import javafx.scene.chart.AreaChart;
import java.time.Month;
import java.util.ArrayList;
public class Coordinator {

    // pass the selected chart from main con down to the handler
    private CoordinatorCharts handlerChart = new CoordinatorCharts();
    public void setChart(AreaChart<String, Number> chart) {
        handlerChart.setChart(chart);
    }

    ReadData readData = new ReadData();

    private static final String SPENDINGS_FILE = "Spendings.txt";
    private static final String EARNINGS_FILE = "Earnings.txt";

    // year / month user picked
    String selectedYear, selectedMonth;

    private enum TransactionType {
        EARNINGS, SPENDINGS
    } TransactionType transactionType;


    private enum ButtonType {
        YEARS, MONTHS
    } ButtonType buttonType;


    private enum Period {
        DAILY, MONTHLY, YEARLY
    } Period period;


// public "apis" used by buttons controller

    //  set transaction type
    public void setTransTypeEarnings() {transactionType = TransactionType.EARNINGS;}
    public void setTransTypeSpendings() {transactionType = TransactionType.SPENDINGS;}

    // set button type
    public void buttonTypeYears() {buttonType = ButtonType.YEARS;}
    public void buttonTypeMonths() {buttonType = ButtonType.MONTHS;}

    // stores users selected year / month
    public void setSelectedYear(String year) {selectedYear = year;}
    public void setSelectedMonth(String month) {selectedMonth = month;}

    // sets the period user wants to view used by buttons con
    public void setPeriodDaily(){period = Period.DAILY;}
    public void setPeriodMonthly(){period = Period.MONTHLY;}
    public void setPeriodYearly(){period = Period.YEARLY;}

// read data to create dynamic buttons in buttons controller

    public ArrayList<String> getData() {
        ArrayList<String> dataFound = new ArrayList<>();

        if (buttonType.equals(ButtonType.YEARS)) {

            switch (transactionType) {
                case EARNINGS -> dataFound = readData.storedYears(EARNINGS_FILE);
                case SPENDINGS -> dataFound = readData.storedYears(SPENDINGS_FILE);
            }
        }
        if (buttonType.equals(ButtonType.MONTHS)) {

            switch (transactionType) {
                case EARNINGS -> dataFound = readData.returnedMonths(EARNINGS_FILE, selectedYear);
                case SPENDINGS -> dataFound = readData.returnedMonths(SPENDINGS_FILE, selectedYear);
            }
        }
        return dataFound;
    }

    // send the user picks to chart handler which loads data and displays it
    public void applyUserSelectionToChart() {
        switch (period) {
            case DAILY -> {
                handlerChart.setPeriodDaily();
                handlerChart.getDataForChart(getFileNameForTransactionType(),monthNameToNumber(selectedMonth), selectedYear);
            }
            case MONTHLY -> {
                handlerChart.setPeriodMonthly();
                handlerChart.getDataForChart(getFileNameForTransactionType(), null, selectedYear);
            }
            case YEARLY -> {
                handlerChart.setPeriodYearly();
                handlerChart.getDataForChart(getFileNameForTransactionType(), null, null);
            }
        }
    }

    private String getFileNameForTransactionType() {

        switch (transactionType) {
            case EARNINGS -> {
                return EARNINGS_FILE;
            }
            case SPENDINGS -> {
                return SPENDINGS_FILE;
            }
        }
        return null;
    }


    private String monthNameToNumber(String monthName) {
        return String.format("%02d", Month.valueOf(monthName.toUpperCase()).getValue());
    }
}











