package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

/**
 * Represents a panel of an average graph.
 */
public class AverageGraphPanel extends UiPart<Region> {
    private static final String FXML = "AverageGraph.fxml";
    private final Logger logger = LogsCenter.getLogger(AverageGraphPanel.class);

    @FXML
    private LineChart<String, Double> lineChart;

    public AverageGraphPanel() {
        super(FXML);
    }
}
