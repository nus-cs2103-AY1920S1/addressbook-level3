package seedu.address.ui;

import java.time.LocalDate;
import java.util.Map;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.record.RecordType;
import seedu.address.model.statistics.AverageType;

/**
 * Represents a panel of an average graph.
 */
public class AverageGraphPanel extends UiPart<Region> {
    private static final String FXML = "AverageGraph.fxml";
    private static final String TITLE = "%1$s average of %2$s";

    private final Logger logger = LogsCenter.getLogger(AverageGraphPanel.class);

    @FXML
    private LineChart<String, Double> lineChart;

    public AverageGraphPanel(Map<LocalDate, Double> averageMap, AverageType averageType, RecordType recordType) {
        super(FXML);

        lineChart.setLegendVisible(false);

        String recordLabel = getRecordLabel(recordType);
        lineChart.setTitle(String.format(TITLE, averageType.toString(), recordLabel));

        String xAxisLabel = getXAxisLabel(averageType);
        lineChart.getXAxis().setLabel(xAxisLabel);

        String yAxisLabel = getYAxisLabel(recordType);
        lineChart.getYAxis().setLabel(yAxisLabel);

        XYChart.Series<String, Double> dataSeries = new XYChart.Series<>();
        for (Map.Entry<LocalDate, Double> entry : averageMap.entrySet()) {
            LocalDate date = entry.getKey();
            Double average = entry.getValue();
            dataSeries.getData().add(new XYChart.Data<String, Double>(date.toString(), average));
        }
        lineChart.getData().add(dataSeries);
    }

    private String getRecordLabel(RecordType recordType) {
        switch (recordType) {
        case BMI:
            return "weight";
        case BLOODSUGAR:
            return "blood sugar";
        default:
            return "";
        }
    }

    private String getYAxisLabel(RecordType recordType) {
        switch (recordType) {
        case BMI:
            return "Weight (kg)";
        case BLOODSUGAR:
            return "Bloodsugar (mmol/L)";
        default:
            return "";
        }
    }

    private String getXAxisLabel(AverageType averageType) {
        switch (averageType) {
        case DAILY:
            return "Day";
        case MONTHLY:
            return "Month";
        case WEEKLY:
            return "Week";
        default:
            return "";
        }
    }
}
