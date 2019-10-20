package seedu.address.ui;

import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Region;

// TODO: Use images to style chart haha
/**
 * An UI component that displays the policy popularity breakdown in the display panel.
 */
public class BarChartVisual extends UiPart<Region> {
    private static final String FXML = "BarChartVisual.fxml";

    @FXML
    private BarChart<String, Integer> barchart;

    public BarChartVisual(ObservableMap<String, Integer> map, String title) {
        super(FXML);
        XYChart.Series<String, Integer> series = getData(map);
        barchart.getData().add(series);
        barchart.setTitle(title);
    }

    private XYChart.Series<String, Integer> getData(ObservableMap<String, Integer> map) {
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        map.forEach((key, value) ->
            series.getData().add(new XYChart.Data<>(key, value))
        );
        return series;
    }
}
