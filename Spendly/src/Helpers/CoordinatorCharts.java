package Helpers;
import DataReaders.ReadTransactions;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import java.time.Month;
import java.util.ArrayList;

public class CoordinatorCharts {

    private enum Period{
        DAILY, MONTHLY, YEARLY
    } Period period;

    // called by Coordinator to set chart ref
    AreaChart<String, Number> chart;
    public void setChart(AreaChart<String, Number> chartReturned) {
        this.chart = chartReturned;
    }

    // ref to data reader
    ReadTransactions readTransactions = new ReadTransactions();

// period setters used by Coordinator
    public void setPeriodDaily(){period = Period.DAILY;}
    public void setPeriodMonthly(){period = Period.MONTHLY;}
    public void setPeriodYearly(){period = Period.YEARLY;}


    public void getDataForChart(String fileName, String selectedMonth, String selectedYear) {

        ArrayList<DataPoint> data = new ArrayList<>();
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        switch (period) {
            case DAILY -> {
                readTransactions.setPeriodDaily();
                data = readTransactions.data(fileName, selectedMonth, selectedYear);
                series.setName("Currently viewing: " + numToMonth(selectedMonth) + " - " + selectedYear);
            }
            case MONTHLY -> {
                readTransactions.setPeriodMonthly();
                data = readTransactions.data(fileName, null, selectedYear);
                series.setName("Currently viewing: " + selectedYear);
            }
            case YEARLY -> {
                readTransactions.setPeriodYearly();
                data = readTransactions.data(fileName, null, null);
                series.setName("Currently viewing all years stored");
            }
        }
        displayTheData(data, series);
    }


    private void displayTheData(ArrayList<DataPoint> data, XYChart.Series<String, Number> series){
        chart.getData().clear();

        for (int i = 0; i < data.size(); i++){
            DataPoint dp= data.get(i);
            String xAxis;

            // show months instead of numbers for monthly view
            if (period == Period.MONTHLY) {
                xAxis = numToMonth(String.valueOf(dp.timeUnitValue()));
            } else {
                xAxis = String.valueOf(dp.timeUnitValue());
            }
            int amount = dp.amount();
            series.getData().add(new XYChart.Data<>(xAxis, amount));
        }

        chart.getData().add(series);
        chart.setLegendVisible(true);
    }

    private String numToMonth(String num){
        String monthName = Month.of(Integer.parseInt(num)).name();
        monthName = monthName.substring(0, 3);
        return monthName;
    }
}
