package dukecooks.ui;

import java.time.format.DateTimeFormatter;

import dukecooks.model.workout.history.ExerciseRun;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * Ui Component that displays information of a {@code ExerciseRun}
 */
public class ExerciseRunCard extends UiPart<Region> {

    private static final String FXML = "ExerciseRunCard.fxml";

    private final ExerciseRun run;

    @FXML
    private Label workoutName;
    @FXML
    private Label timeStarted;
    @FXML
    private Label setsAttempted;
    @FXML
    private Label setsCompleted;
    @FXML
    private Label totalTimeTaken;

    public ExerciseRunCard(ExerciseRun run) {
        super(FXML);
        this.run = run;
        workoutName.setText("Exercise done in workout: " + run.getWorkoutName().workoutName);
        timeStarted.setText("Time: " + run.getTimeStarted()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        setsAttempted.setText("Number of Sets attempted: "
                + run.getSetsAttempted());
        setsCompleted.setText("Number of Sets completed: "
                + run.getSetsCompleted());
        totalTimeTaken.setText("Total time taken: " + run.getTotalTimeTakenString());
    }
}
