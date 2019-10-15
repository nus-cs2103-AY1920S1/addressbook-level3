package seedu.address.testutil;

import java.util.Set;

import seedu.address.logic.commands.EditExerciseCommand;
import seedu.address.logic.commands.EditExerciseCommand.EditExerciseDescriptor;
import seedu.address.model.details.ExerciseDetail;
import seedu.address.model.details.unit.DistanceUnit;
import seedu.address.model.details.unit.WeightUnit;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.exercise.ExerciseName;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditExerciseDescriptorBuilder {

    private EditExerciseCommand.EditExerciseDescriptor descriptor;

    public EditExerciseDescriptorBuilder() {
        descriptor = new EditExerciseDescriptor();
    }

    public EditExerciseDescriptorBuilder(EditExerciseCommand.EditExerciseDescriptor descriptor) {
        this.descriptor = new EditExerciseDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditExerciseDescriptorBuilder(Exercise exercise) {
        descriptor = new EditExerciseCommand.EditExerciseDescriptor();
        descriptor.setExerciseName(exercise.getExerciseName());
        descriptor.setPrimaryMuscle(exercise.getMusclesTrained().getPrimaryMuscle());
        descriptor.setIntensity(exercise.getIntensity());
        descriptor.setExerciseDetails(exercise.getExerciseDetails());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditExerciseDescriptorBuilder withName(String name) {
        descriptor.setExerciseName(new ExerciseName(name));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditExerciseDescriptorBuilder withDetails(Float weight, WeightUnit weightUnit, Float distance,
                                                     DistanceUnit distanceUnit, Integer reps, Integer sets) {

        Set<ExerciseDetail> exerciseDetailSet = SampleDataUtil.getDetails(weight, weightUnit, distance, distanceUnit,
                reps, sets);
        descriptor.setExerciseDetails(exerciseDetailSet);
        return this;
    }

    public EditExerciseCommand.EditExerciseDescriptor build() {
        return descriptor;
    }
}
