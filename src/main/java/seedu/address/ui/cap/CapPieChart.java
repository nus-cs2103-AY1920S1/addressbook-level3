package seedu.address.ui.cap;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of persons.
 */
public class CapPieChart extends UiPart<Region> {
    private static final String FXML = "CapPieChart.fxml";

    @FXML
    private PieChart gradesPieChart;

    public CapPieChart(ObservableList<Data> grades) {
        super(FXML);
        gradesPieChart.setData(grades);
        gradesPieChart.setLabelsVisible(true);
    }
}
