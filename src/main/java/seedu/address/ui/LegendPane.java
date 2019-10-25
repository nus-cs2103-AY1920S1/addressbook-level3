package seedu.address.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

/**
 * Represents ui of a custom chart legend.
 */
public class LegendPane extends UiPart<Region> {

    private static final String FXML = "LegendPane.fxml";

    private static final String RECORD_TYPE_BMI = "bmi";
    private static final String RECORD_TYPE_BLOODSUGAR = "bloodsugar";

    // Color for symbols
    private static final Color COLOR_BLUE = Color.BLUE.deriveColor(1, 1, 1, 0.2);
    private static final Color COLOR_GREEN = Color.GREEN.deriveColor(1, 1, 1, 0.2);
    private static final Color COLOR_YELLOW = Color.YELLOW.deriveColor(1, 1, 1, 0.2);
    private static final Color COLOR_RED = Color.RED.deriveColor(1, 1, 1, 0.2);

    // Weight categorization description
    private static final String UNDER_WEIGHT = "Under weight";
    private static final String NORMAL_WEIGHT = "Normal weight";
    private static final String OVER_WEIGHT = "Over weight";
    private static final String OBESE = "Obese weight";

    // Blood sugar categorization description
    private static final String BEFORE_MEALS = "Before meals";
    private static final String AFTER_MEALS = "At least 90 minutes after meals";


    // Weight legend row
    private static final LegendRow UNDER_WEIGHT_LEGEND_ROW = new LegendRow(COLOR_BLUE, UNDER_WEIGHT);
    private static final LegendRow NORMAL_WEIGHT_LEGEND_ROW = new LegendRow(COLOR_GREEN, NORMAL_WEIGHT);
    private static final LegendRow OVER_WEIGHT_LEGEND_ROW = new LegendRow(COLOR_YELLOW, OVER_WEIGHT);
    private static final LegendRow OBESE_LEGEND_ROW = new LegendRow(COLOR_RED, OBESE);

    // Blood sugar legend row
    private static final LegendRow BEFORE_MEAL_LEGEND_ROW = new LegendRow(COLOR_GREEN, BEFORE_MEALS);
    private static final LegendRow AFTER_MEAL_LEGEND_ROW = new LegendRow(COLOR_YELLOW, AFTER_MEALS);

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
        switch (recordType.get().toLowerCase()) {
        case RECORD_TYPE_BMI:
            flowPane.getChildren().addAll(UNDER_WEIGHT_LEGEND_ROW.getRoot(), NORMAL_WEIGHT_LEGEND_ROW.getRoot(),
                    OVER_WEIGHT_LEGEND_ROW.getRoot(), OBESE_LEGEND_ROW.getRoot());
            break;
        case RECORD_TYPE_BLOODSUGAR:
            flowPane.getChildren().addAll(BEFORE_MEAL_LEGEND_ROW.getRoot(), AFTER_MEAL_LEGEND_ROW.getRoot());
            break;
        default:
            break;
        }
    }

}
