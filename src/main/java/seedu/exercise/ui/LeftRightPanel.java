package seedu.exercise.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.exercise.model.resource.Regime;

/**
 * Controller for panel which has two text area stacked horizontally.
 */
public class LeftRightPanel extends UiPart<Region> {

    private static final String FXML = "LeftRightPanel.fxml";
    private static final String LEFT_PANEL_TITLE_TEXT = "Scheduled Regime: %1$s";
    private static final String RIGHT_PANEL_TITLE_TEXT = "Conflicting Regime: %1$s";

    private ExerciseListPanel leftPanel;
    private ExerciseListPanel rightPanel;

    @FXML
    private StackPane leftPanelPlaceholder;

    @FXML
    private StackPane rightPanelPlaceholder;

    public LeftRightPanel() {
        super(FXML);
    }

    public void setLeftPanel(Regime scheduleRegime) {
        leftPanel = new ExerciseListPanel(scheduleRegime.getRegimeExercises().asUnmodifiableObservableList());
        leftPanel.setPanelTitleText(String.format(LEFT_PANEL_TITLE_TEXT,
                scheduleRegime.getRegimeName().toString()));
        leftPanelPlaceholder.getChildren().add(leftPanel.getRoot());
    }

    public void setRightPanel(Regime conflictingRegime) {
        rightPanel = new ExerciseListPanel(conflictingRegime.getRegimeExercises().asUnmodifiableObservableList());
        rightPanel.setPanelTitleText(String.format(RIGHT_PANEL_TITLE_TEXT,
                conflictingRegime.getRegimeName().toString()));
        rightPanelPlaceholder.getChildren().add(rightPanel.getRoot());
    }

    /**
     * Clears text for both left and right text areas
     */
    public void clearAll() {
        leftPanel = null;
        rightPanel = null;
        leftPanelPlaceholder.getChildren().clear();
        rightPanelPlaceholder.getChildren().clear();
    }
}
