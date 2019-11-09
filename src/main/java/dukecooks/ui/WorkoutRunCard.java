package dukecooks.ui;

import java.time.format.DateTimeFormatter;

import dukecooks.model.workout.history.WorkoutRun;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code WorkoutRun}.
 */
public class WorkoutRunCard extends UiPart<Region> {

    private static final String FXML = "WorkoutRunCard.fxml";

    private final WorkoutRun run;

    @FXML
    private Label time;
    @FXML
    private Label totalExercisesCompleted;
    @FXML
    private Label totalTimeTaken;

    public WorkoutRunCard(WorkoutRun run) {
        super(FXML);
        this.run = run;
        time.setText("Time: " + run.getTimeStarted()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        totalExercisesCompleted.setText("Total Exercises Completed: "
                + run.getTotalExercisesCompleted());
        totalTimeTaken.setText("Total time taken: " + run.getTotalTimeTakenString());
    }
}
