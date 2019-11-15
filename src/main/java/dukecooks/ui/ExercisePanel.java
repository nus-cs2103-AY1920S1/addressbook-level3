package dukecooks.ui;

import java.util.logging.Logger;

import dukecooks.commons.core.LogsCenter;
import dukecooks.model.workout.exercise.components.Exercise;
import dukecooks.model.workout.history.ExerciseRun;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;

/**
 * Ui component showing the contents of an Exercise.
 */
public class ExercisePanel extends UiPart<Region> {

    private static final String FXML = "ExercisePanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ExercisePanel.class);
    private final Exercise exercise;

    @FXML
    private Label name;
    @FXML
    private Label intensity;
    @FXML
    private Label primaryMuscle;
    @FXML
    private Label noTimesRan;
    @FXML
    private Label averageRunTime;
    @FXML
    private FlowPane secondaryMuscles;
    @FXML
    private FlowPane exerciseDetails;
    @FXML
    private ListView<ExerciseRun> runs;

    public ExercisePanel(Exercise exercise) {
        super(FXML);
        this.exercise = exercise;
        name.setText(exercise.getExerciseName().exerciseName);
        intensity.setText("Intensity: " + exercise.getIntensity().toString());
        primaryMuscle.setText("Primary Muscle: " + exercise.getMusclesTrained()
                .getPrimaryMuscle().muscleType);
        noTimesRan.setText("Number of times completed: " + String.valueOf(exercise
                .getHistory().getNoTimesRan()));
        averageRunTime.setText("Average Run Time: " + exercise
                .getHistory().getAverageRunTimeString());
        initSecondaryMuscles();
        initExerciseDetails();
        initRuns();
    }

    /**
     * Initiaises secondaryMuscles from relevant values in exercise.
     */
    private void initSecondaryMuscles() {
        secondaryMuscles.getChildren().add(new Label("Secondary Muscles: "));
        exercise.getMusclesTrained().getSecondaryMuscles().stream()
                .forEach(muscle -> secondaryMuscles.getChildren()
                        .add(new Label(muscle.toView())));
    }

    /**
     * Initialises exerciseDetails from relevant values in exercise.
     */

    private void initExerciseDetails() {
        exerciseDetails.getChildren().add(new Label("Exercise Details: "));
        exercise.getExerciseDetails().stream()
                .forEach(detail -> exerciseDetails.getChildren()
                        .add(new Label(detail.toString())));
    }

    /**
     * Initialises workoutRuns from relevant values in Workout.
     */
    private void initRuns() {
        ObservableList<ExerciseRun> observableRuns = FXCollections
                .observableArrayList(exercise.getHistory().getPreviousRuns());
        runs.setItems(observableRuns);
        runs.setCellFactory(listView -> new ExerciseRunListViewCell());
    }


    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Workout} using a {@code WorkoutRunCard}.
     */

    class ExerciseRunListViewCell extends ListCell<ExerciseRun> {

        @Override
        protected void updateItem(ExerciseRun exerciseRun, boolean empty) {
            super.updateItem(exerciseRun, empty);

            if (empty || exercise == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ExerciseRunCard(exerciseRun).getRoot());
            }
        }
    }
}
