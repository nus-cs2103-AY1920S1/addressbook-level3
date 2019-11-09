package seedu.address.financialtracker.model.util;

import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

/**
 * Statistics for FinancialTracker.
 */
public interface FinancialTrackerStatistics {
    /**
     * Get pie chart data for financial tracker.
     * @return pie chart data
     */
    ObservableList<PieChart.Data> getFinancialPieChartData();

    /**
     * Get bar chart data for financial tracker.
     * @return bar chart data
     */
    XYChart.Series<String, Number>getFinancialBarChartData();
}
