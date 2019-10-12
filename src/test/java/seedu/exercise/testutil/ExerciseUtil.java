package seedu.exercise.testutil;

import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CALORIES;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_MUSCLE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_UNIT;

import java.util.Set;

import seedu.exercise.logic.commands.AddExerciseCommand;
import seedu.exercise.logic.commands.EditCommand;
import seedu.exercise.model.exercise.Exercise;
import seedu.exercise.model.exercise.Muscle;

/**
 * A utility class for Exercise.
 */
public class ExerciseUtil {

    /**
     * Returns an add command string for adding the {@code exercise}.
     */
    public static String getAddCommand(Exercise exercise) {
        return AddExerciseCommand.COMMAND_WORD + " " + getExerciseDetails(exercise);
    }

    /**
     * Returns the part of command string for the given {@code exercise}'s details.
     */
    private static String getExerciseDetails(Exercise exercise) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_CATEGORY + "exercise ");
        sb.append(PREFIX_NAME + exercise.getName().fullName + " ");
        sb.append(PREFIX_DATE + exercise.getDate().toString() + " ");
        sb.append(PREFIX_CALORIES + exercise.getCalories().value + " ");
        sb.append(PREFIX_QUANTITY + exercise.getQuantity().value + " ");
        sb.append(PREFIX_UNIT + exercise.getUnit().unit + " ");
        exercise.getMuscles().stream().forEach(
            s -> sb.append(PREFIX_MUSCLE + s.muscleName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditExerciseDescriptor}'s details.
     */
    public static String getEditExerciseDescriptorDetails(EditCommand.EditExerciseDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getDate().ifPresent(date -> sb.append(PREFIX_DATE).append(date.toString()).append(" "));
        descriptor.getCalories().ifPresent(calories -> sb.append(PREFIX_CALORIES).append(calories.value).append(" "));
        descriptor.getQuantity().ifPresent(quantity -> sb.append(PREFIX_QUANTITY).append(quantity.value).append(" "));
        descriptor.getUnit().ifPresent(unit -> sb.append(PREFIX_UNIT).append(unit.unit).append(" "));
        if (descriptor.getMuscles().isPresent()) {
            Set<Muscle> muscles = descriptor.getMuscles().get();
            if (muscles.isEmpty()) {
                sb.append(PREFIX_MUSCLE);
            } else {
                muscles.forEach(s -> sb.append(PREFIX_MUSCLE).append(s.muscleName).append(" "));
            }
        }
        return sb.toString();
    }
}
