package dukecooks.model.workout;

import dukecooks.ui.UiPart;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import java.util.Comparator;

public class WorkoutCard extends UiPart<Region> {

    private final static String FXML = "WorkoutListCard.fxml";

    public final Workout workout;

    @FXML
    private HBox cardPane;
    @FXML
    private Label workoutName;
    @FXML
    private FlowPane exercises;
    @FXML
    private FlowPane musclesTrained;
    @FXML
    private Label id;
    @FXML
    private Label averageIntensity;

    public WorkoutCard(Workout workout, int displayedIndex) {
        super(FXML);
        this.workout = workout;
        id.setText(displayedIndex + ". ");
        workoutName.setText(workout.getName().workoutName);
        workout.getExercises().stream()
                .sorted(Comparator.comparing(exercise -> exercise.toString()))
                .forEach(exercise -> exercises.getChildren().add(new Label(exercise.getExerciseName().exerciseName)));
        workout.getMusclesTrained().stream()
                .sorted(Comparator.comparing(muscleType -> muscleType.toString()))
                .forEach(muscleType -> musclesTrained.getChildren().add(new Label(muscleType.getMuscleType())));
        averageIntensity.setText("Average Intensity: " + workout.getAverageIntensity().toString());
    }
}
