package seedu.exercise.ui;

import java.util.Optional;
import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.exercise.commons.core.LogsCenter;
import seedu.exercise.model.resource.Exercise;

/**
 * Panel containing the list of exercises.
 */
public class ExerciseListPanel extends UiPart<Region> {
    private static final String FXML = "ExerciseListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

    private ObservableList<Exercise> exerciseList;

    @FXML
    private ListView<Exercise> exerciseListView;

    public ExerciseListPanel(ObservableList<Exercise> exerciseList) {
        super(FXML);
        this.exerciseList = exerciseList;
        exerciseListView.setItems(exerciseList);
        exerciseListView.setCellFactory(listView -> new ExerciseListViewCell());

        exerciseList.addListener((ListChangeListener<Exercise>) change -> {
            while (change.next()) {
                if (change.wasReplaced()) {
                    int indexChanged = change.getFrom();
                    logger.info("Exercise " + indexChanged + " is replaced.");
                    selectGivenIndex(indexChanged);
                } else if (change.wasAdded()) {
                    int indexAdded = exerciseList.size() - 1;
                    logger.info("Exercise " + indexAdded + " is added.");
                    selectGivenIndex(indexAdded);
                }
            }
        });
    }

    public boolean isExerciseSelected() {
        return exerciseListView.getSelectionModel().getSelectedIndex() >= 0;
    }

    public Optional<Exercise> getSelectedExercise() {
        if (isExerciseSelected()) {
            return Optional.of(exerciseListView.getSelectionModel().getSelectedItem());
        } else {
            return Optional.empty();
        }
    }

    public ListView<Exercise> getExerciseListView() {
        return exerciseListView;
    }

    private void selectGivenIndex(int index) {
        exerciseListView.scrollTo(index);
        exerciseListView.getSelectionModel().select(index);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Exercise} using a {@code ExerciseInfoPanel}.
     */
    class ExerciseListViewCell extends ListCell<Exercise> {
        @Override
        protected void updateItem(Exercise exercise, boolean empty) {
            super.updateItem(exercise, empty);

            if (empty || exercise == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ExerciseListCard(exercise, getIndex() + 1).getRoot());
            }
        }
    }
}
