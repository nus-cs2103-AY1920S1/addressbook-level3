package seedu.sugarmummy.ui.statistics;

import static seedu.sugarmummy.commons.core.Messages.MESSAGE_INVALID_RECORD_TYPE;
import static seedu.sugarmummy.ui.statistics.RangeMarkerColor.COLOR_BLUE;
import static seedu.sugarmummy.ui.statistics.RangeMarkerColor.COLOR_GREEN;
import static seedu.sugarmummy.ui.statistics.RangeMarkerColor.COLOR_RED;
import static seedu.sugarmummy.ui.statistics.RangeMarkerColor.COLOR_YELLOW;

import java.time.LocalDate;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.sugarmummy.model.records.RecordType;
import seedu.sugarmummy.ui.UiPart;

//@@author chen-xi-cx

/**
 * Represents ui of a {@code CustomLineChart} legend.
 */
public class LegendPane extends UiPart<Region> {

    private static final String FXML = "LegendPane.fxml";

    // Weight categorization description
    private static final String UNDERWEIGHT = "Underweight: BMI below 18.5";
    private static final String NORMAL_WEIGHT = "Normal weight: BMI between 18.5 and 25";
    private static final String OVERWEIGHT = "Overweight: BMI between 25 and 30";
    private static final String OBESE = "Obese: BMI above 30";

    // Blood sugar categorization description
    private static final String BEFORE_MEALS = "Before meals (Non-diabetic): Between 4.0 and 5.9 mmol/L";
    private static final String AFTER_MEALS = "At least 90 minutes after meals (Non-diabetic):"
            + " Between 5.9 and 7.8 mmol/L";


    // Weight legend row
    private static final LegendRow UNDER_WEIGHT_LEGEND_ROW = new LegendRow(COLOR_BLUE, UNDERWEIGHT);
    private static final LegendRow NORMAL_WEIGHT_LEGEND_ROW = new LegendRow(COLOR_GREEN, NORMAL_WEIGHT);
    private static final LegendRow OVER_WEIGHT_LEGEND_ROW = new LegendRow(COLOR_YELLOW, OVERWEIGHT);
    private static final LegendRow OBESE_LEGEND_ROW = new LegendRow(COLOR_RED, OBESE);

    // Blood sugar legend row
    private static final LegendRow BEFORE_MEAL_LEGEND_ROW = new LegendRow(COLOR_GREEN, BEFORE_MEALS);
    private static final LegendRow AFTER_MEAL_LEGEND_ROW = new LegendRow(COLOR_BLUE, AFTER_MEALS);

    @FXML
    private FlowPane lineChartLegendFlowPane;

    public LegendPane(ObservableMap<LocalDate, Double> averageMap, SimpleStringProperty recordType) {
        super(FXML);

        recordType.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                refreshLegendFlowPane(averageMap, recordType);
            }
        });

        averageMap.addListener(new MapChangeListener<LocalDate, Double>() {
            @Override
            public void onChanged(Change<? extends LocalDate, ? extends Double> change) {
                refreshLegendFlowPane(averageMap, recordType);
            }
        });

        updateLegendPane(averageMap, recordType);
    }

    /**
     * A convenience function to update lineChartLegendFlowPane with new symbols and description whenever averageMap
     * or recordType changes.
     *
     * @param averageMap the newly updated averageMap containing the average values.
     * @param recordType the newly updated type of record.
     */
    private void refreshLegendFlowPane(ObservableMap<LocalDate, Double> averageMap, SimpleStringProperty recordType) {
        lineChartLegendFlowPane.getChildren().clear();
        updateLegendPane(averageMap, recordType);
    }

    /**
     * Calculates the maximum average values and updates lineChartLegendFlowPane with legends
     * that suit the record type and maximum average values given.
     */
    private void updateLegendPane(ObservableMap<LocalDate, Double> averageMap, SimpleStringProperty recordType) {
        double maxAvg = findMax(averageMap);
        switch (RecordType.valueOf(recordType.get())) {
        case BMI:
            if (maxAvg >= 0) {
                lineChartLegendFlowPane.getChildren().add(UNDER_WEIGHT_LEGEND_ROW.getRoot());
            }
            if (maxAvg >= 18.5) {
                lineChartLegendFlowPane.getChildren().add(NORMAL_WEIGHT_LEGEND_ROW.getRoot());
            }
            if (maxAvg >= 25) {
                lineChartLegendFlowPane.getChildren().add(OVER_WEIGHT_LEGEND_ROW.getRoot());
            }
            if (maxAvg >= 30) {
                lineChartLegendFlowPane.getChildren().add(OBESE_LEGEND_ROW.getRoot());
            }
            break;
        case BLOODSUGAR:
            if (maxAvg >= 4.0) {
                lineChartLegendFlowPane.getChildren().add(BEFORE_MEAL_LEGEND_ROW.getRoot());
            }
            if (maxAvg >= 5.9) {
                lineChartLegendFlowPane.getChildren().add(AFTER_MEAL_LEGEND_ROW.getRoot());
            }
            break;
        default:
            assert false : "Record type is not found and it should not happen.";
            throw new IllegalArgumentException(MESSAGE_INVALID_RECORD_TYPE);
        }
    }

    /**
     * Returns the maximum average value in averageMap.
     *
     * @param averageMap a {@code AverageMap} object that maps time period to the respective average values.
     */
    private double findMax(ObservableMap<LocalDate, Double> averageMap) {
        double maxAvg = 0;
        for (Double average : averageMap.values()) {
            maxAvg = Math.max(maxAvg, average);
        }
        return maxAvg;
    }

}
