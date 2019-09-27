package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand;
import seedu.address.model.exercise.Calories;
import seedu.address.model.exercise.Date;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.exercise.Name;
import seedu.address.model.exercise.Quantity;
import seedu.address.model.tag.Muscle;

/**
 * A utility class to help with building EditExerciseDescriptor objects.
 */
public class EditExerciseDescriptorBuilder {

    private EditCommand.EditExerciseDescriptor descriptor;

    public EditExerciseDescriptorBuilder() {
        descriptor = new EditCommand.EditExerciseDescriptor();
    }

    public EditExerciseDescriptorBuilder(EditCommand.EditExerciseDescriptor descriptor) {
        this.descriptor = new EditCommand.EditExerciseDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditExerciseDescriptor} with fields containing {@code exercise}'s details
     */
    public EditExerciseDescriptorBuilder(Exercise exercise) {
        descriptor = new EditCommand.EditExerciseDescriptor();
        descriptor.setName(exercise.getName());
        descriptor.setCalories(exercise.getCalories());
        descriptor.setDate(exercise.getDate());
        descriptor.setQuantity(exercise.getQuantity());
        descriptor.setMuscles(exercise.getMuscles());
    }

    /**
     * Sets the {@code Name} of the {@code EditExerciseDescriptor} that we are building.
     */
    public EditExerciseDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Calories} of the {@code EditExerciseDescriptor} that we are building.
     */
    public EditExerciseDescriptorBuilder withCalories(String calories) {
        descriptor.setCalories(new Calories(calories));
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditExerciseDescriptor} that we are building.
     */
    public EditExerciseDescriptorBuilder withDate(String date) {
        descriptor.setDate(new Date(date));
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code EditExerciseDescriptor} that we are building.
     */
    public EditExerciseDescriptorBuilder withQuantity(String quantity) {
        descriptor.setQuantity(new Quantity(quantity));
        return this;
    }

    /**
     * Parses the {@code muscles} into a {@code Set<Muscle>} and set it to the {@code EditExerciseDescriptor}
     * that we are building.
     */
    public EditExerciseDescriptorBuilder withMuscles(String... muscles) {
        Set<Muscle> muscleSet = Stream.of(muscles).map(Muscle::new).collect(Collectors.toSet());
        descriptor.setMuscles(muscleSet);
        return this;
    }

    public EditCommand.EditExerciseDescriptor build() {
        return descriptor;
    }
}
