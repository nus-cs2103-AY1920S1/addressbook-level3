package dukecooks.ui;

import dukecooks.commons.core.LogsCenter;
import dukecooks.model.workout.Workout;
import dukecooks.model.workout.WorkoutCard;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import java.util.logging.Logger;

public class WorkoutListPanel extends UiPart<Region> {
    private static final String FXML = "WorkoutListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ExerciseListPanel.class);

    @FXML
    private ListView<Workout> workoutListView;

    public WorkoutListPanel(ObservableList<Workout> exerciseList) {
        super(FXML);
        workoutListView.setItems(exerciseList);
        workoutListView.setCellFactory(listView -> new WorkoutListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class WorkoutListViewCell extends ListCell<Workout> {

        @Override
        protected void updateItem(Workout workout, boolean empty) {
            super.updateItem(workout, empty);

            if (empty || workout == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new WorkoutCard(workout, getIndex() + 1).getRoot());
            }
        }
    }
}
