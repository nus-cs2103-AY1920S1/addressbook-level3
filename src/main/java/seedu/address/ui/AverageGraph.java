package seedu.address.ui;

import java.time.LocalDate;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import seedu.address.model.statistics.CustomLineChart;

/**
 * Represents ui of an average graph with no legend.
 */
public class AverageGraph {
    private static final String FXML = "AverageGraph.fxml";
    private static final String TITLE = "%1$s average of %2$s";

    // average type
    private static final String AVERAGE_TYPE_DAILY = "daily";
    private static final String AVERAGE_TYPE_WEEKLY = "weekly";
    private static final String AVERAGE_TYPE_MONTHLY = "monthly";

    // record type
    private static final String RECORD_TYPE_BMI = "bmi";
    private static final String RECORD_TYPE_BLOODSUGAR = "bloodsugar";

    private static final String WEIGHT = "weight";
    private static final String BLOODSUGAR = "blood sugar";

    // units
    private static final String BLOODSUGAR_UNIT = " (mmol/L)";
    private static final String WEIGHT_UNIT = " (kg)";

    private static final String DAY = "day";
    private static final String WEEK = "week";
    private static final String MONTH = "month";

    private static final String UNKNOWN = "unknown";

    private final CategoryAxis xAxis = new CategoryAxis();
    private final NumberAxis yAxis = new NumberAxis();

    private final CustomLineChart<String, Number> customLineChart = new CustomLineChart<>(xAxis, yAxis);

    public AverageGraph(ObservableMap<LocalDate, Double> averageMap, SimpleStringProperty averageType,
                             SimpleStringProperty recordType) {

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
    }

    /**
     * A convenience function to create a new chart.
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
        String recordLabel = getTitleInRecord(recordType);
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
    private String getTitleInRecord(SimpleStringProperty recordType) {
        switch (recordType.get().toLowerCase()) {
        case RECORD_TYPE_BMI:
            return WEIGHT;
        case RECORD_TYPE_BLOODSUGAR:
            return BLOODSUGAR;
        default:
            return UNKNOWN;
        }
    }

    /**
     * Gets the record type to be used for y axis label.
     * Adds horizontal range marker if record type is blood sugar.
     */
    private String getYAxisLabel(SimpleStringProperty recordType) {
        switch (recordType.get().toLowerCase()) {
        case RECORD_TYPE_BMI:
            return WEIGHT + WEIGHT_UNIT;
        case RECORD_TYPE_BLOODSUGAR:
            XYChart.Data<Number, Number> horizontalRangeMarker = new XYChart.Data<>(4.0, 5.4);
            customLineChart.addHorizontalRangeMarker(horizontalRangeMarker, Color.GREEN);
            return BLOODSUGAR + BLOODSUGAR_UNIT;
        default:
            return UNKNOWN;
        }
    }

    /**
     * Gets the average type to be used for x axis label.
     */
    private String getXAxisLabel(SimpleStringProperty averageType) {
        switch (averageType.get().toLowerCase()) {
        case AVERAGE_TYPE_DAILY:
            return DAY;
        case AVERAGE_TYPE_WEEKLY:
            return WEEK;
        case AVERAGE_TYPE_MONTHLY:
            return MONTH;
        default:
            return UNKNOWN;
        }
    }

    public CustomLineChart<String, Number> getAverageGraph() {
        return customLineChart;
    }
}
