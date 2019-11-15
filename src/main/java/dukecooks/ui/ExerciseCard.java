package dukecooks.ui;

import java.util.Comparator;

import dukecooks.model.workout.exercise.components.Exercise;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class ExerciseCard extends UiPart<Region> {

    private static final String FXML = "ExerciseListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Exercise exercise;

    @FXML
    private HBox cardPane;
    @FXML
    private Label exerciseName;
    @FXML
    private Label primaryMuscle;
    @FXML
    private Label secondaryMuscles;
    @FXML
    private Label id;
    @FXML
    private Label intensity;
    @FXML
    private FlowPane details;

    public ExerciseCard(Exercise exercise, int displayedIndex) {
        super(FXML);
        this.exercise = exercise;
        id.setText(displayedIndex + ". ");
        exerciseName.setText(exercise.getExerciseName().exerciseName);
        if (!exercise.getMusclesTrained().getSecondaryMuscles().isEmpty()) {
            secondaryMuscles.setText(getSecondaryLabelText());
        } else {
            secondaryMuscles.setText("Secondary Muscles: None");
        }
        primaryMuscle.setText("Primary Muscle: " + exercise.getMusclesTrained().getPrimaryMuscle());
        intensity.setText("Intensity: " + exercise.getIntensity().toString());
        exercise.getExerciseDetails().stream()
                .sorted(Comparator.comparing(detail -> detail.toString()))
                .forEach(detail -> details.getChildren().add(new Label(detail.toString())));
    }

    /**
     * Returns text for secondary muscle label
     */
    private String getSecondaryLabelText() {
        StringBuilder builder = new StringBuilder();
        builder.append("Secondary Muscles: ");
        exercise.getMusclesTrained().getSecondaryMuscles().stream()
                .sorted(Comparator.comparing(muscleType -> muscleType.toString()))
                .forEach(muscleType -> builder.append(muscleType).append(", "));
        builder.delete(builder.length() - 2, builder.length() - 1);
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExerciseCard)) {
            return false;
        }

        // state check
        ExerciseCard card = (ExerciseCard) other;
        return id.getText().equals(card.id.getText())
                && exercise.equals(card.exercise);
    }
}
