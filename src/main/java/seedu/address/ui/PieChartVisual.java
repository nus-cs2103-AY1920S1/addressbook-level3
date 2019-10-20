package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Region;

/**
 * An UI component that displays the policy popularity breakdown in the display panel.
 */
public class PieChartVisual extends UiPart<Region> {
    private static final String FXML = "PieChartVisual.fxml";

    @FXML
    private PieChart piechart;

    public PieChartVisual(ObservableMap<String, Integer> map) {
        super(FXML);
        ObservableList<PieChart.Data> pieChartData = getData(map);
        piechart.setData(pieChartData);
    }

    private ObservableList<PieChart.Data> getData(ObservableMap<String, Integer> map) {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        map.forEach((key, value) ->
            data.add(new PieChart.Data(key, value))
        );
        return data;
    }
}
