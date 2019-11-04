package dukecooks.ui;

import dukecooks.model.workout.exercise.ExerciseSetAttempt;
import dukecooks.model.workout.exercise.components.ExerciseName;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Panel containing the exercise set attempts.
 */
public class ExerciseSetAttemptListPanel extends UiPart<Region> {

    private static final String FXML = "ExerciseSetAttemptPanel.fxml";

    private ObservableList<ExerciseSetAttempt> attempts;

    @FXML
    private Label name;

    @FXML
    private ListView<ExerciseSetAttempt> exerciseSetAttemptListView;

    public ExerciseSetAttemptListPanel(ObservableList<ExerciseSetAttempt> attempts,
                                       ExerciseName name) {
        super(FXML);
        this.attempts = attempts;
        this.name.setText(name.exerciseName);
        exerciseSetAttemptListView.setItems(attempts);
        exerciseSetAttemptListView.setCellFactory(listView -> new ExerciseSetAttemptListViewCell());
    }

    public ObservableList<ExerciseSetAttempt> getAttempts() {
        return attempts;
    }

    public void setAttemptIsDone(int index, boolean isDone) {
        ExerciseSetAttempt newAttempt = attempts.get(index).clone();
        newAttempt.setDone(isDone);
        attempts.remove(index);
        attempts.add(index, newAttempt);
    }

    /**
     * Display inner components within ExerciseSetAttemptListPanel.
     * Make use of boolean variables to decide which components to show/hide.
     */
    public void showPanels(boolean showIndexView, boolean showCardView) {
        exerciseSetAttemptListView.setVisible(showIndexView);
        exerciseSetAttemptListView.setManaged(showIndexView);
        exerciseSetAttemptListView.setVisible(showCardView);
        exerciseSetAttemptListView.setManaged(showCardView);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class ExerciseSetAttemptListViewCell extends ListCell<ExerciseSetAttempt> {

        @Override
        protected void updateItem(ExerciseSetAttempt attempt, boolean empty) {
            super.updateItem(attempt, empty);

            if (empty || attempt == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ExerciseSetAttemptCard(attempt, getIndex() + 1).getRoot());
            }
        }
    }

}
