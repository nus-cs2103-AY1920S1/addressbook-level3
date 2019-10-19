package seedu.address.logic.commands.exercise;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.Model;
import seedu.address.model.exercise.components.IntensityContainsKeywordsPredicate;

/**
 * Finds and lists all Exercises in Duke Workout Planner whose Intensity contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindExerciseByIntensityCommand extends FindCommand {

    public static final String COMMAND_WORD = "findIntensity";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all exercises with any intensity "
            + "specified by the user (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: INTENSITY [MORE_INTENSITY]... \n"
            + "Example: " + COMMAND_WORD + " Low hIGh";

    private final IntensityContainsKeywordsPredicate predicate;

    public FindExerciseByIntensityCommand(IntensityContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredExerciseList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredExerciseList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindExerciseByIntensityCommand // instanceof handles nulls
                && predicate.equals(((FindExerciseByIntensityCommand) other).predicate)); // state check
    }
}
