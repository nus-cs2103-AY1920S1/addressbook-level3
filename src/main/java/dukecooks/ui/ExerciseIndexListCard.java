package dukecooks.ui;

import dukecooks.model.workout.exercise.components.Exercise;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;


/**
 * An UI component that displays information of a {@code Exercise}.
 */
public class ExerciseIndexListCard extends UiPart<Region> {

    private static final String FXML = "ExerciseIndexListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Exercise exercise;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label primaryMuscle;
    @FXML
    private Label intensity;

    public ExerciseIndexListCard(Exercise exercise, int displayedIndex) {
        super(FXML);
        this.exercise = exercise;
        id.setText(displayedIndex + ". ");
        name.setText(exercise.getExerciseName().exerciseName);
        primaryMuscle.setText("Primary Muscle: " + exercise.getMusclesTrained().getPrimaryMuscle().muscleType);
        intensity.setText("Intensity: " + exercise.getIntensity().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExerciseIndexListCard)) {
            return false;
        }

        // state check
        ExerciseIndexListCard card = (ExerciseIndexListCard) other;
        return id.getText().equals(card.id.getText())
                && exercise.equals(card.exercise);
    }
}
