package seedu.exercise.ui;

import static seedu.exercise.ui.util.LabelUtil.setLabelTooltip;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.exercise.model.resource.Exercise;

/**
 * An UI component that displays information of a {@code Exercise}.
 */
public class ExerciseListCard extends UiPart<Region> {

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
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label calories;
    @FXML
    private Label quantityAndUnit;

    @FXML
    private FlowPane tags;

    public ExerciseListCard(Exercise exercise, int displayedIndex) {
        super(FXML);
        this.exercise = exercise;
        id.setText(displayedIndex + ". ");
        name.setText(exercise.getName().fullName);
        date.setText(exercise.getDate().toString());
        calories.setText(exercise.getCalories().toString() + " kcal");
        quantityAndUnit.setText(exercise.getQuantity().toString() + " " + exercise.getUnit().unit);
        exercise.getMuscles().stream()
            .sorted(Comparator.comparing(muscle -> muscle.muscleName))
            .forEach(muscle -> tags.getChildren().add(new Label(muscle.muscleName)));

        setLabelTooltip(name);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExerciseListCard)) {
            return false;
        }

        // state check
        ExerciseListCard card = (ExerciseListCard) other;
        return id.getText().equals(card.id.getText())
            && exercise.equals(card.exercise);
    }
}
