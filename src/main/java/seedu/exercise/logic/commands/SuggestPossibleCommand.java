package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_MUSCLE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_SUGGEST_TYPE;

import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.Model;
import seedu.exercise.model.property.Muscle;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.ui.ListResourceType;

/**
 * Lists possible exercises to the user.
 */
public class SuggestPossibleCommand extends SuggestCommand {

    public static final String MESSAGE_USAGE_SUGGEST_POSSIBLE = "Parameters: "
            + PREFIX_SUGGEST_TYPE + "SUGGEST_TYPE "
            + "[" + PREFIX_MUSCLE + "MUSCLE] "
            + "[" + "CUSTOM_PROPERTY_PREFIX" + "/" + "VALUE]" + "\n"
            + "\t\tExample: " + COMMAND_WORD + " "
            + PREFIX_SUGGEST_TYPE + "possible "
            + PREFIX_MUSCLE + "Legs";

    public static final String MESSAGE_SUCCESS = "Listed suggested exercises.";
    public static final String SUGGEST_TYPE = "possible";


    private Set<Muscle> targetMuscles;
    private Map<String, String> targetCustomPropertiesMap;

    public SuggestPossibleCommand(Set<Muscle> targetMuscles, Map<String, String> targetCustomPropertiesMap) {
        this.targetMuscles = targetMuscles;
        this.targetCustomPropertiesMap = targetCustomPropertiesMap;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateSuggestedExerciseList(getPredicate());
        return new CommandResult(MESSAGE_SUCCESS, ListResourceType.SUGGESTION);
    }

    private Predicate<Exercise> getPredicate() {
        return exercise -> {
            Set<Muscle> exercisesMuscles = exercise.getMuscles();
            for (Muscle muscle : targetMuscles) {
                if (!(exercisesMuscles.contains(muscle))) {
                    return false;
                }
            }

            Map<String, String> exerciseCustomProperties = exercise.getCustomProperties();
            for (String key : targetCustomPropertiesMap.keySet()) {
                if (!(targetCustomPropertiesMap.get(key).equals(exerciseCustomProperties.get(key)))) {
                    return false;
                }
            }
            return true;
        };
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SuggestPossibleCommand); // instanceof handles nulls
    }
}
