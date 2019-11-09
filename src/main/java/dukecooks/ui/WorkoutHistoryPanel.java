package dukecooks.ui;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import dukecooks.commons.core.LogsCenter;
import dukecooks.model.workout.Workout;
import dukecooks.model.workout.exercise.components.ExerciseName;
import dukecooks.model.workout.exercise.details.ExerciseDetail;
import dukecooks.model.workout.history.WorkoutRun;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;

/**
 *  Ui component showing the data stored in workout history.
 */
public class WorkoutHistoryPanel extends UiPart<Region> {

    private static final String FXML = "WorkoutHistoryPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(WorkoutHistoryPanel.class);
    private final Workout workout;

    @FXML
    private Label name;
    @FXML
    private Label intensity;
    @FXML
    private Label noTimesRan;
    @FXML
    private Label averageRunTime;
    @FXML
    private FlowPane musclesTrained;
    @FXML
    private ListView<ExercisePair> exerciseDetails;
    @FXML
    private ListView<WorkoutRun> runs;

    public WorkoutHistoryPanel(Workout workout) {
        super(FXML);
        this.workout = workout;
        name.setText(workout.getName().workoutName);
        intensity.setText("Intensity: " + workout.getAverageIntensity().toString());
        noTimesRan.setText("Number of times completed: " + String.valueOf(workout.getHistory().getNoTimesRan()));
        averageRunTime.setText("Average Run Time: "
                + workout.getHistory().getAverageRunTimeString());
        initMusclesTrained();
        initExercisesDetails();
        initRuns();
    }

    /**
     * Initialises musclesTrained from relevant values in Workout.
     */
    private void initMusclesTrained() {
        musclesTrained.getChildren().add(new Label("Muscles Trained: "));
        workout.getMusclesTrained().stream()
                .forEach(muscle -> musclesTrained.getChildren()
                        .add(new Label(muscle.toView())));
    }

    /**
     * Initialises exercisesDetails from relevant values in Workout.
     */
    private void initExercisesDetails() {
        ObservableList<ExercisePair> pairList = FXCollections.observableArrayList();
        List<ExerciseName> names = workout.getExercises();
        for (ExerciseName name: names) {
            pairList.add(new ExercisePair(name, workout
                    .getExercisesDetails().get(names.indexOf(name))));
        }
        exerciseDetails.setItems(pairList);
        exerciseDetails.setCellFactory(listView -> new ExerciseDetailsListViewCell());
    }

    /**
     * Initialises workoutRuns from relevant values in Workout.
     */
    private void initRuns() {
        ObservableList<WorkoutRun> observableRuns = FXCollections
                .observableArrayList(workout.getHistory().getPreviousRuns());
        runs.setItems(observableRuns);
        runs.setCellFactory(listView -> new WorkoutRunListViewCell());
    }


    /**
     * Makeshift class to pass the details needed into the listView.
     */

    class ExercisePair {

        private final ExerciseName name;
        private final Set<ExerciseDetail> details;

        ExercisePair(ExerciseName name, Set<ExerciseDetail> details) {
            this.name = name;
            this.details = details;
        }

        public ExerciseName getName() {
            return name;
        }

        public Set<ExerciseDetail> getDetails() {
            return details;
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */

    class ExerciseDetailsListViewCell extends ListCell<ExercisePair> {

        @Override
        protected void updateItem(ExercisePair pair, boolean empty) {
            super.updateItem(pair, empty);

            if (empty || workout == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ExerciseDetailsCard(pair).getRoot());
            }
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Workout} using a {@code WorkoutRunCard}.
     */

    class WorkoutRunListViewCell extends ListCell<WorkoutRun> {

        @Override
        protected void updateItem(WorkoutRun workoutRun, boolean empty) {
            super.updateItem(workoutRun, empty);

            if (empty || workout == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new WorkoutRunCard(workoutRun).getRoot());
            }
        }
    }


}
