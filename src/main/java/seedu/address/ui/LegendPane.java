package seedu.address.ui;

import static seedu.address.ui.RangeMarkerColor.COLOR_BLUE;
import static seedu.address.ui.RangeMarkerColor.COLOR_GREEN;
import static seedu.address.ui.RangeMarkerColor.COLOR_RED;
import static seedu.address.ui.RangeMarkerColor.COLOR_YELLOW;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.model.record.RecordType;

/**
 * Represents ui of a custom chart legend.
 */
public class LegendPane extends UiPart<Region> {

    private static final String FXML = "LegendPane.fxml";

    // Weight categorization description
    private static final String UNDERWEIGHT = "Underweight: BMI below 18.5";
    private static final String NORMAL_WEIGHT = "Normal weight: BMI between 18.5 and 25";
    private static final String OVERWEIGHT = "Overweight: BMI between 25 and 30";
    private static final String OBESE = "Obese: BMI above 30";

    // Blood sugar categorization description
    private static final String BEFORE_MEALS = "Non-diabetic (before meals): Between 4.0 and 5.9";
    private static final String AFTER_MEALS = "Non-diabetic (At least 90 minutes after meals): Between 5.9 and 7.8";

    // Weight legend row
    private static final LegendRow UNDER_WEIGHT_LEGEND_ROW = new LegendRow(COLOR_BLUE, UNDERWEIGHT);
    private static final LegendRow NORMAL_WEIGHT_LEGEND_ROW = new LegendRow(COLOR_GREEN, NORMAL_WEIGHT);
    private static final LegendRow OVER_WEIGHT_LEGEND_ROW = new LegendRow(COLOR_YELLOW, OVERWEIGHT);
    private static final LegendRow OBESE_LEGEND_ROW = new LegendRow(COLOR_RED, OBESE);

    // Blood sugar legend row
    private static final LegendRow BEFORE_MEAL_LEGEND_ROW = new LegendRow(COLOR_GREEN, BEFORE_MEALS);
    private static final LegendRow AFTER_MEAL_LEGEND_ROW = new LegendRow(COLOR_BLUE, AFTER_MEALS);

    @FXML
    private FlowPane flowPane;

    public LegendPane(SimpleStringProperty recordType) {
        super(FXML);

        recordType.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                flowPane.getChildren().clear();
                updateLegendPane(recordType);
            }
        });

        updateLegendPane(recordType);
    }

    /**
     * Updates legend box to suit the record type given.
     */
    private void updateLegendPane(SimpleStringProperty recordType) {
        switch (RecordType.valueOf(recordType.get())) {
        case BMI:
            flowPane.getChildren().addAll(UNDER_WEIGHT_LEGEND_ROW.getRoot(), NORMAL_WEIGHT_LEGEND_ROW.getRoot(),
                    OVER_WEIGHT_LEGEND_ROW.getRoot(), OBESE_LEGEND_ROW.getRoot());
            break;
        case BLOODSUGAR:
            flowPane.getChildren().addAll(BEFORE_MEAL_LEGEND_ROW.getRoot(), AFTER_MEAL_LEGEND_ROW.getRoot());
            break;
        default:
            // will not happen
            assert false : "Record type is not supported.";
        }
    }

}
