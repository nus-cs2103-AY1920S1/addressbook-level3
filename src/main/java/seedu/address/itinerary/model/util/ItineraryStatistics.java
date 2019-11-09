package seedu.address.itinerary.model.util;

import javafx.scene.chart.XYChart;

public interface ItineraryStatistics {
    int getTotalItineraryEntries();

    XYChart.Series<String, Number> getItineraryBarChartData();
}
