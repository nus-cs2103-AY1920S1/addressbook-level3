package seedu.address.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Represents ui of a legend box.
 */
public class LegendBox extends UiPart<Region> {

    private static final String FXML = "LegendBox.fxml";

    private static final String RECORD_TYPE_BMI = "bmi";
    private static final String RECORD_TYPE_BLOODSUGAR = "bloodsugar";

    // Weight categorization
    private static final Color COLOR_UNDER_WEIGHT = Color.BLUE.deriveColor(1, 1, 1, 0.2);
    private static final Color COLOR_NORMAL_WEIGHT = Color.GREEN.deriveColor(1, 1, 1, 0.2);
    private static final Color COLOR_OVER_WEIGHT = Color.YELLOW.deriveColor(1, 1, 1, 0.2);
    private static final Color COLOR_OBESE = Color.RED.deriveColor(1, 1, 1, 0.2);

    // Blood sugar categorization
    private static final Color COLOR_BEFORE_MEALS = Color.GREEN.deriveColor(1, 1, 1, 0.2);
    private static final Color COLOR_AFTER_MEALS = Color.YELLOW.deriveColor(1, 1, 1, 0.2);

    // Weight categorization description
    private static final String UNDER_WEIGHT = "Under weight";
    private static final String NORMAL_WEIGHT = "Normal weight";
    private static final String OVER_WEIGHT = "Over weight";
    private static final String OBESE = "Obese weight";

    // Blood sugar categorization description
    private static final String BEFORE_MEALS = "Before meals";
    private static final String AFTER_MEALS = "At least 90 minutes after meals";


    // Weight legend row
    private static final LegendRow UNDER_WEIGHT_LEGEND_ROW = new LegendRow(COLOR_UNDER_WEIGHT, UNDER_WEIGHT);
    private static final LegendRow NORMAL_WEIGHT_LEGEND_ROW = new LegendRow(COLOR_NORMAL_WEIGHT, NORMAL_WEIGHT);
    private static final LegendRow OVER_WEIGHT_LEGEND_ROW = new LegendRow(COLOR_OVER_WEIGHT, OVER_WEIGHT);
    private static final LegendRow OBESE_LEGEND_ROW = new LegendRow(COLOR_OBESE, OBESE);

    // Blood sugar legend row
    private static final LegendRow BEFORE_MEAL_LEGEND_ROW = new LegendRow(COLOR_BEFORE_MEALS, BEFORE_MEALS);
    private static final LegendRow AFTER_MEAL_LEGEND_ROW = new LegendRow(COLOR_AFTER_MEALS, AFTER_MEALS);

    @FXML
    private VBox vBox;

    public LegendBox(SimpleStringProperty recordType) {
        super(FXML);

        recordType.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                vBox.getChildren().clear();
                updateLegendBox(recordType);
            }
        });
        updateLegendBox(recordType);
    }

    /**
     * Updates legend box to suit the record type given.
     */
    private void updateLegendBox(SimpleStringProperty recordType) {
        switch (recordType.get().toLowerCase()) {
        case RECORD_TYPE_BMI:
            vBox.getChildren().addAll(UNDER_WEIGHT_LEGEND_ROW.getRoot(), NORMAL_WEIGHT_LEGEND_ROW.getRoot(),
                    OVER_WEIGHT_LEGEND_ROW.getRoot(), OBESE_LEGEND_ROW.getRoot());
            break;
        case RECORD_TYPE_BLOODSUGAR:
            vBox.getChildren().addAll(BEFORE_MEAL_LEGEND_ROW.getRoot(), AFTER_MEAL_LEGEND_ROW.getRoot());
            break;
        default:
            break;
        }
    }

}
