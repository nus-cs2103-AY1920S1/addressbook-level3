package seedu.exercise.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.exercise.model.resource.Exercise;

/**
 * Controller for panel which has two text area stacked horizontally.
 */
public class LeftRightPanel extends UiPart<Region> {

    private static final String FXML = "LeftRightPanel.fxml";

    private ExerciseListPanel leftPanel;
    private ExerciseListPanel rightPanel;

    @FXML
    private StackPane leftPanelPlaceholder;

    @FXML
    private StackPane rightPanelPlaceholder;

    public LeftRightPanel() {
        super(FXML);
    }

    public void setLeftPanel(ObservableList<Exercise> leftExercise) {
        leftPanel = new ExerciseListPanel(leftExercise);
        leftPanelPlaceholder.getChildren().add(leftPanel.getRoot());
    }

    public void setRightPanel(ObservableList<Exercise> rightExercise) {
        rightPanel = new ExerciseListPanel(rightExercise);
        rightPanelPlaceholder.getChildren().add(rightPanel.getRoot());
    }

    /**
     * Clears text for both left and right text areas
     */
    public void clearAll() {
        leftPanel = null;
        rightPanel = null;
    }
}
