package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditExerciseCommand;
import seedu.address.logic.commands.EditExerciseCommand.EditExerciseDescriptor;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.exercise.Name;
import seedu.address.model.details.ExerciseDetail;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditExerciseCommand.EditExerciseDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditExerciseDescriptor();
    }

    public EditPersonDescriptorBuilder(EditExerciseCommand.EditExerciseDescriptor descriptor) {
        this.descriptor = new EditExerciseDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditPersonDescriptorBuilder(Exercise exercise) {
        descriptor = new EditExerciseCommand.EditExerciseDescriptor();
        descriptor.setName(exercise.getName());
        descriptor.setExerciseDetails(exercise.getExerciseDetails());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        Set<ExerciseDetail> exerciseDetailSet = Stream.of(tags).map(ExerciseDetail::new).collect(Collectors.toSet());
        descriptor.setExerciseDetails(exerciseDetailSet);
        return this;
    }

    public EditExerciseCommand.EditExerciseDescriptor build() {
        return descriptor;
    }
}
