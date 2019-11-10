package seedu.billboard.ui.charts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import javafx.scene.chart.XYChart;

/**
 * Manager for the series that an {@code XYChart} displays. Includes operations to update the series set and update
 * each series individually.
 * @param <X>
 * @param <Y>
 */
class SeriesManager<X, Y> {
    private final XYChart<X, Y> chart;
    private final Map<String, XYChart.Series<X, Y>> seriesMap;


    SeriesManager(Set<String> seriesNames, XYChart<X, Y> chart) {
        this.chart = chart;
        this.seriesMap = new HashMap<>();
        generateSeriesMap(seriesNames);
    }

    void updateSeriesSet(Set<String> seriesNames) {
        if (!seriesMap.keySet().equals(seriesNames)) {
            generateSeriesMap(seriesNames);
        }
    }

    void updateSeries(Consumer<XYChart.Series<X, Y>> seriesUpdater) {
        List<XYChart.Series<X, Y>> unusedSeries = new ArrayList<>();

        for (var series : chart.getData()) {
            String name = series.getName();
            if (seriesMap.containsKey(name)) {
                seriesUpdater.accept(series);
            } else {
                unusedSeries.add(series);
            }
        }
        chart.getData().removeAll(unusedSeries);
    }

    /**
     * Idempotent method which updates {@code seriesMap} with new series names input set and generates new series
     * based on those names to replace the current series in the chart.
     */
    private void generateSeriesMap(Set<String> seriesNames) {
        seriesMap.clear();
        for (var name : seriesNames) {
            XYChart.Series<X, Y> series = new XYChart.Series<>();
            series.setName(name);
            seriesMap.put(name, series);
        }

        chart.getData().setAll(seriesMap.values());
    }
}
