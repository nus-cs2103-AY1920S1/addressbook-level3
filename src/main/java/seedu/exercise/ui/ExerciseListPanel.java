package seedu.exercise.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.exercise.commons.core.LogsCenter;
import seedu.exercise.model.exercise.Exercise;

/**
 * Panel containing the list of exercises.
 */
public class ExerciseListPanel extends UiPart<Region> {
    private static final String FXML = "ExerciseListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ExerciseListPanel.class);

    @FXML
    private ListView<Exercise> exerciseListView;

    public ExerciseListPanel(ObservableList<Exercise> exerciseList) {
        super(FXML);
        exerciseListView.setItems(exerciseList);
        exerciseListView.setCellFactory(listView -> new ExerciseListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Exercise} using a {@code ExerciseCard}.
     */
    class ExerciseListViewCell extends ListCell<Exercise> {
        @Override
        protected void updateItem(Exercise exercise, boolean empty) {
            super.updateItem(exercise, empty);

            if (empty || exercise == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ExerciseCard(exercise, getIndex() + 1).getRoot());
            }
        }
    }

}
