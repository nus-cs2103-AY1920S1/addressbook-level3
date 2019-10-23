package seedu.address.ui.statistics;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;

/**
 * A UI component that displays statistics.
 */
public class StackBarChart extends UiPart<Region> {
    private static final String FXML = "StatsBarChart.fxml";

    @FXML
    private StackedBarChart bc;
    @FXML
    private Label overview;

    public StackBarChart(ObservableList<XYChart.Data> data, int totalNotes) {
        super(FXML);
        bc.setLegendSide(Side.LEFT);
        bc.setData(data);
        overview.setText("The total number of questions done so far: " + totalNotes + "\n");
    }

    public StackedBarChart getChart() {
        return bc;
    }
}
