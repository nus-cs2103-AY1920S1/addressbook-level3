package seedu.address.ui;

import java.time.LocalDate;
import java.util.Map;
import java.util.logging.Logger;

import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
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

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;


    public AverageGraphPanel(ObservableMap<LocalDate, Double> averageMap, AverageType averageType, RecordType recordType) {
        super(FXML);

        averageMap.addListener(new MapChangeListener<LocalDate, Double>() {
            @Override
            public void onChanged(Change<? extends LocalDate, ? extends Double> change) {
                lineChart.getData().clear();
                createChart(averageMap, averageType, recordType);
            }
        });

        lineChart.setAnimated(false);

        lineChart.setLegendVisible(false);

        createChart(averageMap, averageType, recordType);
    }

    /**
     * A convenience function to display chart.
     */
    private void createChart(ObservableMap<LocalDate, Double> averageMap, AverageType averageType, RecordType recordType) {
        setTitle(averageType, recordType);

        setAxesLabel(averageType, recordType);

        loadAndShowChart(averageMap);
    }

    /**
     * Sets chart title.
     */
    private void setTitle(AverageType averageType, RecordType recordType) {
        String recordLabel = getRecordLabel(recordType);
        lineChart.setTitle(String.format(TITLE, averageType.toString(), recordLabel));
    }

    /**
     * Sets labels of x and y axes according to average type and record type.
     */
    private void setAxesLabel(AverageType averageType, RecordType recordType) {
        String xAxisLabel = getXAxisLabel(averageType);
        xAxis.setLabel(xAxisLabel);

        String yAxisLabel = getYAxisLabel(recordType);
        yAxis.setLabel(yAxisLabel);
    }

    /**
     * Loads data from averageMap and display in chart.
     */
    private void loadAndShowChart(ObservableMap<LocalDate, Double> averageMap) {
        XYChart.Series<String, Double> dataSeries = new XYChart.Series<>();
        for (Map.Entry<LocalDate, Double> entry : averageMap.entrySet()) {
            LocalDate date = entry.getKey();
            Double average = entry.getValue();
            dataSeries.getData().add(new XYChart.Data<String, Double>(date.toString(), average));
        }
        lineChart.getData().add(dataSeries);
    }

    /**
     * Gets the record type to be used in chart title.
     */
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
