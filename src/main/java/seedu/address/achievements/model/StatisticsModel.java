package seedu.address.achievements.model;

import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

/**
 * The API of the StatisticsModel component.
 */
public interface StatisticsModel {

    /**
     * Get statistics of number of total contacts in Address Book.
     * @return total number of contacts in Address Book
     */
    int getTotalPersons();

    /**
     * Horizontal bar chart data for Address Book.
     * @return horizontal bar chart data for Address Book
     */
    XYChart.Series<Number, String> getAddressChartData();

    /**
     * Get statistics of number of total diary entries in Diary Book.
     * @return total number of diary entries in Diary Book.
     */
    int getTotalDiaryEntries();

    /**
     * Line chart data for Diary Book.
     * @return line chart data for Diary Book
     */
    XYChart.Series<String, Number> getDiaryChartData();

    ObservableList<PieChart.Data> getFinancialPieChartData();

    XYChart.Series<String, Number> getFinancialBarChartData();

    int getTotalItineraryEntries();

    XYChart.Series<String, Number> getItineraryLineChartData();

    long getNumberOfDaysTrip();

    long getNumberOfDaysVacation();

    long getNumberOfTrip();

    double getPercentageTrip();
}
