package seedu.exercise.ui;

import static seedu.exercise.ui.util.LabelUtil.setLabelTooltip;

import java.util.Comparator;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.exercise.model.property.Muscle;
import seedu.exercise.model.resource.Exercise;

/**
 * An UI component that displays information of a {@code Exercise}.
 */
public class ExerciseInfoPanel extends UiPart<Region> {

    private static final String FXML = "ExerciseInfoPanel.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    private Exercise exercise;

    @FXML
    private AnchorPane cardPane;

    // Exercise information
    @FXML
    private Label name;
    @FXML
    private Label date;
    @FXML
    private Label calories;
    @FXML
    private Label quantityAndUnit;
    @FXML
    private FlowPane muscles;
    @FXML
    private StackPane customProperties;

    private CustomPropertyListPanel customPropertiesList;

    public ExerciseInfoPanel(Exercise exercise) {
        super(FXML);
        this.exercise = exercise;
        name.setText(exercise.getName().fullName);
        date.setText(exercise.getDate().toString());
        calories.setText(exercise.getCalories().toString() + " kcal");
        quantityAndUnit.setText(exercise.getQuantity().toString() + " " + exercise.getUnit().unit);
        setMuscleTags(exercise.getMuscles());
        customPropertiesList = new CustomPropertyListPanel(exercise.getObservableCustomPropertiesList());
        customProperties.getChildren().add(customPropertiesList.getRoot());
        setLabelTooltip(name);
        setLabelTooltip(calories);
        setLabelTooltip(quantityAndUnit);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExerciseInfoPanel)) {
            return false;
        }

        // state check
        ExerciseInfoPanel card = (ExerciseInfoPanel) other;
        return exercise.equals(card.exercise);
    }

    private void setMuscleTags(Set<Muscle> musclesSet) {
        if (musclesSet.isEmpty()) {
            muscles.getChildren().add(new Label("N/A"));
        } else {
            exercise.getMuscles().stream()
                .sorted(Comparator.comparing(muscle -> muscle.muscleName))
                .forEach(muscle -> muscles.getChildren().add(new Label(muscle.muscleName)));
        }
    }
}
