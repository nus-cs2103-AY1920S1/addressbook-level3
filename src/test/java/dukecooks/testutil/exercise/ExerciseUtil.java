package dukecooks.testutil.exercise;

import java.util.Set;

import dukecooks.logic.commands.exercise.AddExerciseCommand;
import dukecooks.logic.commands.exercise.EditExerciseCommand;
import dukecooks.logic.parser.CliSyntax;
import dukecooks.model.workout.exercise.components.Exercise;
import dukecooks.model.workout.exercise.details.Distance;
import dukecooks.model.workout.exercise.details.ExerciseDetail;
import dukecooks.model.workout.exercise.details.ExerciseWeight;
import dukecooks.model.workout.exercise.details.Repetitions;

/**
 * A utility class for Person.
 */
public class ExerciseUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddExerciseCommand(Exercise exercise) {
        return AddExerciseCommand.COMMAND_WORD + " " + AddExerciseCommand.VARIANT_WORD + " "
                + getExerciseDetails(exercise);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getExerciseDetails(Exercise exercise) {
        StringBuilder sb = new StringBuilder();
        sb.append(CliSyntax.PREFIX_NAME + exercise.getExerciseName().exerciseName + " ")
            .append(CliSyntax.PREFIX_PRIMARY_MUSCLE + exercise.getMusclesTrained().getPrimaryMuscle().muscleType + " ")
            .append(CliSyntax.PREFIX_INTENSITY + exercise.getIntensity().toString());
        exercise.getExerciseDetails().stream().forEach(
            s -> appendDetail(s, sb)
        );
        return sb.toString();
    }

    /**
     * Checks the instance of detail and adds the corresponding prefix of the detail
     * to details
     */
    private static void appendDetail(ExerciseDetail detail, StringBuilder sb) {
        if (detail instanceof ExerciseWeight) {
            sb.append(CliSyntax.PREFIX_WEIGHT + detail
                    .getMagnitude().toString() + " " + ((ExerciseWeight) detail).getUnit().toString());
        } else if (detail instanceof Distance) {
            sb.append(CliSyntax.PREFIX_DISTANCE + detail.getMagnitude().toString() + " "
                    + ((Distance) detail).getUnit().toString());
        } else if (detail instanceof Repetitions) {
            sb.append(CliSyntax.PREFIX_REPETITIONS + detail.getMagnitude().toString());
        } else {
            sb.append(CliSyntax.PREFIX_SETS + detail.getMagnitude().toString());
        }
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditExerciseCommand.EditExerciseDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getExerciseName()
                .ifPresent(name -> sb.append(CliSyntax.PREFIX_NAME).append(name.exerciseName).append(" "));
        if (descriptor.getExerciseDetails().isPresent()) {
            Set<ExerciseDetail> exerciseDetails = descriptor.getExerciseDetails().get();
            if (!exerciseDetails.isEmpty()) {
                exerciseDetails.forEach(s -> appendDetail(s, sb));
            }
        }
        return sb.toString();
    }
}
