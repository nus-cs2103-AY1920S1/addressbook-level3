package seedu.address.itinerary.model.util;

import javafx.scene.chart.XYChart;

/**
 * Statistics for Itinerary.
 */
public interface ItineraryStatistics {
    /**
     * Gets total number of itinerary entries.
     * @return total number of itinerary entries
     */
    int getTotalItineraryEntries();

    /**
     * Gets bar chart data for itinerary.
     * @return bar chart data for itinerary
     */
    XYChart.Series<String, Number> getItineraryBarChartData();
}
