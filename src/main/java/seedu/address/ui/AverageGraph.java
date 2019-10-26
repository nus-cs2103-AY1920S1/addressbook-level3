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
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import seedu.address.model.statistics.CustomLineChart;

/**
 * Represents ui of an average graph with no legend.
 */
public class AverageGraph {
    private static final String TITLE = "%1$s average of %2$s";

    // average type
    private static final String AVERAGE_TYPE_DAILY = "daily";
    private static final String AVERAGE_TYPE_WEEKLY = "weekly";
    private static final String AVERAGE_TYPE_MONTHLY = "monthly";

    // record type
    private static final String RECORD_TYPE_BMI = "bmi";
    private static final String RECORD_TYPE_BLOODSUGAR = "bloodsugar";

    private static final String BMI = "BMI";
    private static final String BLOODSUGAR = "blood sugar";

    // units
    private static final String BLOODSUGAR_UNIT = " (mmol/L)";

    private static final String DAY = "day";
    private static final String WEEK = "week";
    private static final String MONTH = "month";

    private static final String UNKNOWN = "unknown";

    // Horizontal range marker for BMI
    private static final XYChart.Data<Number, Number> UNDER_WEIGHT_MARKER = new XYChart.Data<>(0, 18.5);
    private static final XYChart.Data<Number, Number> NORMAL_WEIGHT_MARKER = new XYChart.Data<>(18.5, 25);
    private static final XYChart.Data<Number, Number> OVER_WEIGHT_MARKER = new XYChart.Data<>(25, 30);
    private static final XYChart.Data<Number, Number> OBESE_WEIGHT_MARKER = new XYChart.Data<>(30, Double.MAX_VALUE);

    // Horizontal range marker for blood sugar
    private static final XYChart.Data<Number, Number> BEFORE_MEALS = new XYChart.Data<>(4.0, 5.9);
    private static final XYChart.Data<Number, Number> AFTER_MEALS = new XYChart.Data<>(5.9, 7.8);

    // Color for symbols
    private static final Color COLOR_BLUE = Color.BLUE.deriveColor(1, 1, 1, 0.25);
    private static final Color COLOR_GREEN = Color.GREEN.deriveColor(1, 1, 1, 0.25);
    private static final Color COLOR_YELLOW = Color.YELLOW.deriveColor(1, 1, 1, 0.25);
    private static final Color COLOR_RED = Color.RED.deriveColor(1, 1, 1, 0.25);

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

        customLineChart.setId("customLineChart");
        customLineChart.setAnimated(false);

        VBox.setVgrow(customLineChart, Priority.ALWAYS);

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

        addHorizontalRangeMarker(recordType);
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
            return BMI;
        case RECORD_TYPE_BLOODSUGAR:
            return BLOODSUGAR;
        default:
            return UNKNOWN;
        }
    }

    /**
     * Gets the record type to be used for y axis label.
     */
    private String getYAxisLabel(SimpleStringProperty recordType) {
        switch (recordType.get().toLowerCase()) {
        case RECORD_TYPE_BMI:
            return BMI;
        case RECORD_TYPE_BLOODSUGAR:
            return BLOODSUGAR + BLOODSUGAR_UNIT;
        default:
            return UNKNOWN;
        }
    }

    /**
     * Adds horizontal range marker to denote ranges of values for given record type.
     * @param recordType the record type of specified by user.
     */
    private void addHorizontalRangeMarker(SimpleStringProperty recordType) {
        switch (recordType.get().toLowerCase()) {
        case RECORD_TYPE_BMI:
            customLineChart.addHorizontalRangeMarker(UNDER_WEIGHT_MARKER, COLOR_BLUE);
            customLineChart.addHorizontalRangeMarker(NORMAL_WEIGHT_MARKER, COLOR_GREEN);
            customLineChart.addHorizontalRangeMarker(OVER_WEIGHT_MARKER, COLOR_YELLOW);
            customLineChart.addHorizontalRangeMarker(OBESE_WEIGHT_MARKER, COLOR_RED);
            break;
        case RECORD_TYPE_BLOODSUGAR:
            customLineChart.addHorizontalRangeMarker(BEFORE_MEALS, COLOR_GREEN);
            customLineChart.addHorizontalRangeMarker(AFTER_MEALS, COLOR_BLUE);
            break;
        default:
            break;
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
