package seedu.address.ui;

import java.time.LocalDate;
import java.util.Map;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
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

    public AverageGraphPanel(Map<LocalDate, Double> averageMap) {
        super(FXML);
        XYChart.Series<String, Double> dataSeries = new XYChart.Series<>();
        for (Map.Entry<LocalDate, Double> entry : averageMap.entrySet()) {
            LocalDate key = entry.getKey();
            Double value = entry.getValue();
            dataSeries.getData().add(new XYChart.Data<String, Double>("jan", value));
        }
        lineChart.getData().add(dataSeries);
    }
}
