package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * A UI component that displays statistics.
 */
public class StatsChart extends UiPart<Region> {
    private static final String FXML = "StatsChart.fxml";

    @FXML
    private PieChart pc;
    @FXML
    private Label overview;

    public StatsChart(ObservableList<PieChart.Data> data) {
        super(FXML);
        pc.setLegendSide(Side.LEFT);
        pc.setData(data);
        overview.setText("The total number of questions answered so far: " + data.size() + "\n" //change this later
                + "Number of questions answered correctly: " + (int) data.get(0).getPieValue() + "\n"
                + "Number of questions answered incorrectly: " + (int) data.get(1).getPieValue() + "\n");
    }

    public PieChart getChart() {
        return pc;
    }
}
