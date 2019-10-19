package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

/**
 * Displays report with key KPIs
 */
public class ReportPanel extends UiPart<Region> {
    private static final String FXML = "ReportPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ReportPanel.class);

    @FXML
    private LineChart<Number, Number> lineChart;

    public ReportPanel() {
        super(FXML);
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        lineChart = new LineChart<>(xAxis, yAxis);
    }
}
