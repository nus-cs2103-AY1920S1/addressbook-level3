package dukecooks.ui;

import java.util.Comparator;

import dukecooks.model.workout.Workout;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;


/**
 * An UI component that displays information of a {@code Workout}.
 */
public class WorkoutCard extends UiPart<Region> {

    private static final String FXML = "WorkoutListCard.fxml";

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
                .forEach(exercise -> exercises.getChildren().add(new Label(exercise.exerciseName)));
        workout.getMusclesTrained().stream()
                .sorted(Comparator.comparing(muscleType -> muscleType.toString()))
                .forEach(muscleType -> musclesTrained.getChildren().add(new Label(muscleType.getMuscleType())));
        averageIntensity.setText("Average Intensity: " + workout.getAverageIntensity().toString());
    }
}
