package seedu.address.ui;

import java.time.LocalDate;
import java.util.Map;
import java.util.logging.Logger;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.statistics.CustomLineChart;

/**
 * Represents a panel of an average graph.
 */
public class AverageGraphPanel extends UiPart<Region> {
    private static final String FXML = "AverageGraph.fxml";
    private static final String TITLE = "%1$s average of %2$s";

    private final Logger logger = LogsCenter.getLogger(AverageGraphPanel.class);

    private final CategoryAxis xAxis = new CategoryAxis();
    private final NumberAxis yAxis = new NumberAxis();

    private final CustomLineChart<String, Number> customLineChart = new CustomLineChart<>(xAxis, yAxis);

    @FXML
    private ScrollPane scrollPane;

    public AverageGraphPanel(ObservableMap<LocalDate, Double> averageMap, SimpleStringProperty averageType,
                             SimpleStringProperty recordType) {
        super(FXML);

        averageMap.addListener(new MapChangeListener<LocalDate, Double>() {
            @Override
            public void onChanged(Change<? extends LocalDate, ? extends Double> change) {
                customLineChart.getData().clear();
                customLineChart.removeAllHorizontalRangeMarker();
                createChart(averageMap, averageType, recordType);
            }
        });

        averageType.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                customLineChart.getData().clear();
                customLineChart.removeAllHorizontalRangeMarker();
                createChart(averageMap, averageType, recordType);
            }
        });

        recordType.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                customLineChart.getData().clear();
                customLineChart.removeAllHorizontalRangeMarker();
                createChart(averageMap, averageType, recordType);
            }
        });

        customLineChart.setAnimated(false);

        customLineChart.setLegendVisible(false);



        createChart(averageMap, averageType, recordType);
        scrollPane.setContent(customLineChart);

    }

    /**
     * A convenience function to display chart.
     */
    private void createChart(ObservableMap<LocalDate, Double> averageMap, SimpleStringProperty averageType,
                             SimpleStringProperty recordType) {
        setTitle(averageType, recordType);

        setAxesLabel(averageType, recordType);

        loadAndShowChart(averageMap);
    }

    /**
     * Sets chart title.
     */
    private void setTitle(SimpleStringProperty averageType, SimpleStringProperty recordType) {
        String recordLabel = getRecordLabel(recordType);
        customLineChart.setTitle(String.format(TITLE, averageType.get(), recordLabel));
    }

    /**
     * Sets labels of x and y axes according to average type and record type.
     */
    private void setAxesLabel(SimpleStringProperty averageType, SimpleStringProperty recordType) {
        String xAxisLabel = getXAxisLabel(averageType);
        xAxis.setLabel(xAxisLabel);

        String yAxisLabel = getYAxisLabel(recordType);
        yAxis.setLabel(yAxisLabel);
    }

    /**
     * Loads data from averageMap and display in chart.
     */
    private void loadAndShowChart(ObservableMap<LocalDate, Double> averageMap) {
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        for (Map.Entry<LocalDate, Double> entry : averageMap.entrySet()) {
            LocalDate date = entry.getKey();
            Double average = entry.getValue();
            dataSeries.getData().add(new XYChart.Data<String, Number>(date.toString(), average));
        }
        customLineChart.getData().add(dataSeries);
    }

    /**
     * Gets the record type to be used in chart title.
     */
    private String getRecordLabel(SimpleStringProperty recordType) {
        switch (recordType.get().toUpperCase()) {
        case "BMI":
            return "weight";
        case "BLOODSUGAR":
            return "blood sugar";
        default:
            return "";
        }
    }

    /**
     * Gets the record type to be used for y axis label.
     * Adds horizontal range marker if record type is blood sugar.
     */
    private String getYAxisLabel(SimpleStringProperty recordType) {
        switch (recordType.get().toUpperCase()) {
        case "BMI":
            return "Weight (kg)";
        case "BLOODSUGAR":
            XYChart.Data<Number, Number> horizontalRangeMarker = new XYChart.Data<>(4.0, 5.4);
            customLineChart.addHorizontalRangeMarker(horizontalRangeMarker, Color.GREEN);
            return "Bloodsugar (mmol/L)";
        default:
            return "";
        }
    }

    /**
     * Gets the average type to be used for x axis label.
     */
    private String getXAxisLabel(SimpleStringProperty averageType) {
        switch (averageType.get().toUpperCase()) {
        case "DAILY":
            return "Day";
        case "MONTHLY":
            return "Month";
        case "WEEKLY":
            return "Week";
        default:
            return "";
        }
    }
}
