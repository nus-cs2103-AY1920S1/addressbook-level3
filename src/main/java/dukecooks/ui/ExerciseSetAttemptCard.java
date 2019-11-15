package dukecooks.ui;

import dukecooks.model.workout.exercise.ExerciseSetAttempt;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;


/**
 * An UI component that displays information of a {@code ExerciseSetAttempt}.
 */
public class ExerciseSetAttemptCard extends UiPart<Region> {

    private static final String FXML = "SetDetailCard.fxml";

    private final ExerciseSetAttempt attempt;

    @FXML
    private Label id;
    @FXML
    private Label reps;
    @FXML
    private Label time;
    @FXML
    private Label weight;
    @FXML
    private Label distance;
    @FXML
    private Label restTime;
    @FXML
    private Label isDoneLabel;

    public ExerciseSetAttemptCard(ExerciseSetAttempt attempt, int displayedIndex) {
        super(FXML);
        this.attempt = attempt;
        id.setText(String.valueOf(displayedIndex));
        if (!attempt.getReps().equals(ExerciseSetAttempt.DUMMY_REPS)) {
            reps.setText(attempt.getReps().toString());
        }
        if (!attempt.getTime().equals(ExerciseSetAttempt.DUMMY_TIME)) {
            time.setText(attempt.getTime().toString());
        }
        if (!attempt.getWeight().equals(ExerciseSetAttempt.DUMMY_WEIGHT)) {
            weight.setText(attempt.getWeight().toString());
        }
        if (!attempt.getDistance().equals(ExerciseSetAttempt.DUMMY_DISTANCE)) {
            distance.setText(attempt.getDistance().toString());
        }
        if (!attempt.getRestTime().equals(ExerciseSetAttempt.DUMMY_TIME)) {
            restTime.setText(attempt.getRestTime().toString());
        }
        if (attempt.isDone()) {
            isDoneLabel.setText("Done!");
        }
    }
}
