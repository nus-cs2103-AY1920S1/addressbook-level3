package seedu.address.ui;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Region;

/**
 * A UI component that displays statistics.
 */
public class StatsChart extends UiPart<Region> {

    private static final String FXML = "StatsChart.fxml";
    private List data;
    private PieChart pc;

    @FXML
    private PieChart piechart;

    public StatsChart(ObservableList<PieChart.Data> data) {
        super(FXML);
        this.data = data;
        pc = new PieChart(data);
        pc.setLegendSide(Side.LEFT);
        pc.setLabelsVisible(false);
        this.pc = pc;
    }

    public PieChart getChart() {
        return pc;
    }
}
