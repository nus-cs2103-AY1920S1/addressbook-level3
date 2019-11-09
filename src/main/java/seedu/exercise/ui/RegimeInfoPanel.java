package seedu.exercise.ui;

import static seedu.exercise.ui.util.LabelUtil.setLabelTooltip;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.exercise.model.resource.Regime;

/**
 * Information panel for regime type resources
 */
public class RegimeInfoPanel extends UiPart<Region> {

    private static final String FXML = "RegimeInfoPanel.fxml";
    private static final String EXERCISE_LIST_PANEL_TEXT = "List of Exercises";
    private static final String REGIME_NAME_TEXT = "Regime %1$s";
    private static final String TOTAL_CALORIE_COUNT_TEXT = "Total Calories: %1$d";

    private Regime regime;

    @FXML
    private Label regimeName;

    @FXML
    private Label totalCalorieCount;

    @FXML
    private StackPane exerciseListPanelPlaceholder;

    public RegimeInfoPanel(Regime regime) {
        super(FXML);

        this.regimeName.setText(String.format(REGIME_NAME_TEXT, regime.getRegimeName().toString()));
        this.totalCalorieCount.setText(String.format(TOTAL_CALORIE_COUNT_TEXT, regime.getTotalCalorieCount()));

        ExerciseListPanel exerciseListPanel = new ExerciseListPanel(
                regime.getRegimeExercises().asUnmodifiableObservableList());
        exerciseListPanel.setPanelTitleText(EXERCISE_LIST_PANEL_TEXT);
        exerciseListPanelPlaceholder.getChildren().add(exerciseListPanel.getRoot());
        setLabelTooltip(regimeName);
    }
}
