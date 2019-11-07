package dukecooks.ui;

import java.util.logging.Logger;

import dukecooks.commons.core.LogsCenter;
import dukecooks.model.workout.Workout;
import dukecooks.model.workout.exercise.components.Exercise;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of workouts.
 */
public class WorkoutListPanel extends UiPart<Region> {
    private static final String FXML = "WorkoutListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ExerciseListPanel.class);

    @FXML
    private ListView<Exercise> exerciseListView;

    @FXML
    private ListView<Workout> workoutListView;


    public WorkoutListPanel(ObservableList<Workout> workoutList, ObservableList<Exercise> exerciseList) {
        super(FXML);
        workoutListView.setItems(workoutList);
        workoutListView.setCellFactory(listView -> new WorkoutListViewCell());
        exerciseListView.setItems(exerciseList);
        exerciseListView.setCellFactory(listView -> new ExerciseListPanel.ExerciseListViewCell());
    }

    /**
     * Switch view within Workout Panel.
     */
    @FXML
    void handleSwitch(String type) {
        switch (type) {
        case "all":
            showPanels(true, true);
            break;
        default:
            throw new AssertionError("Something's Wrong! Invalid Workout Records page type!");
        }
    }

    /**
     * Display inner components within Health Record Panel.
     * Make use of boolean variables to decide which components to show/hide.
     */
    private void showPanels(boolean showIndexView, boolean showCardView) {
        workoutListView.setVisible(showIndexView);
        workoutListView.setManaged(showIndexView);
        workoutListView.setVisible(showCardView);
        workoutListView.setManaged(showCardView);
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
