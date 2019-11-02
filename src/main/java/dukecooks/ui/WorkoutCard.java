package dukecooks.ui;

import java.util.ArrayList;
import java.util.Comparator;

import dukecooks.model.Image;
import dukecooks.model.workout.Workout;

import dukecooks.model.workout.history.WorkoutRun;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

import javax.swing.text.html.ImageView;


/**
 * An UI component that displays information of a {@code Workout}.
 */
public class WorkoutCard extends UiPart<Region> {

    private static final String HIGH_FXML = "HighIntensityWorkoutPanel.fxml";
    private static final String MEDIUM_FXML = "MediumIntensityWorkoutPanel.fxml";
    private static final String LOW_FXML = "LowIntensityWorkoutPanel.fxml";

    public final Workout workout;

    @FXML
    private HBox cardPane;
    @FXML
    private Text workoutName;
    @FXML
    private Text noExercises;
    @FXML
    private Text lastDone;

    public WorkoutCard(Workout workout, int displayedIndex) {
        super(fillIntensity(workout));
        this.workout = workout;
        workoutName.setText(displayedIndex + ". " + workout.getName().workoutName);
        noExercises.setText("No. Exercises: " + workout.getExercises().size());
        if (!workout.getHistory().getPreviousRuns().isEmpty()) {
            lastDone.setText("Last Done: " + workout.getHistory().getMostRecentRun().getTimeEnded());
        }
    }

    private static String fillIntensity(Workout workout) {
        switch (workout.getAverageIntensity()) {

        case LOW:
            return LOW_FXML;

        case MEDIUM:
            return MEDIUM_FXML;

        default:
            return HIGH_FXML;
        }
    }
}
