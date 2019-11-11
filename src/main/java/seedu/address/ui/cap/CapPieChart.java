package seedu.address.ui.cap;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

/**
 * Encapsulates the piechart ui containing the list of grades breakdown.
 */
public class CapPieChart extends UiPart<Region> {
    private static final String FXML = "CapPieChart.fxml";
    private final Logger logger = LogsCenter.getLogger(CapPieChart.class);

    @FXML
    private PieChart gradesPieChart;

    public CapPieChart(ObservableList<Data> grades) {
        super(FXML);
        requireNonNull(grades);

        gradesPieChart.setData(grades);
        gradesPieChart.setLabelsVisible(true);
        gradesPieChart.getData().forEach(data -> {
            String percentage = String.format("%.2f%%", (data.getPieValue() / 100));
            Tooltip toolTip = new Tooltip(percentage);
            Tooltip.install(data.getNode(), toolTip);
        });
    }

    /**
     * Update the current set of grades to update the pie chart.
     * @param grades valid set of grades.
     */
    public void setPieChartUpdate(ObservableList<Data> grades) {
        requireNonNull(grades);
        gradesPieChart.setData(grades);

    }
}
