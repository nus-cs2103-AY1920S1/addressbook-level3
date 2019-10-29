package seedu.address.achievements.model;

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
    XYChart.Series<Integer, String> getAddressChartData();
}
