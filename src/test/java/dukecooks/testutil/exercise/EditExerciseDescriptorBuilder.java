package dukecooks.testutil.exercise;

import java.util.Set;

import dukecooks.logic.commands.exercise.EditExerciseCommand;
import dukecooks.model.util.SampleDataUtil;
import dukecooks.model.workout.exercise.components.Exercise;
import dukecooks.model.workout.exercise.components.ExerciseName;
import dukecooks.model.workout.exercise.details.ExerciseDetail;
import dukecooks.model.workout.exercise.details.unit.DistanceUnit;
import dukecooks.model.workout.exercise.details.unit.WeightUnit;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditExerciseDescriptorBuilder {

    private EditExerciseCommand.EditExerciseDescriptor descriptor;

    public EditExerciseDescriptorBuilder() {
        descriptor = new EditExerciseCommand.EditExerciseDescriptor();
    }

    public EditExerciseDescriptorBuilder(EditExerciseCommand.EditExerciseDescriptor descriptor) {
        this.descriptor = new EditExerciseCommand.EditExerciseDescriptor(descriptor);
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
        descriptor.setExerciseHistory(exercise.getHistory());
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
