package dukecooks.logic.commands.exercise;

import static java.util.Objects.requireNonNull;

import dukecooks.commons.core.Messages;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.FindCommand;
import dukecooks.model.Model;
import dukecooks.model.workout.exercise.components.ExerciseNameContainsKeywordsPredicate;

/**
 * Finds and lists all Exercises in Duke Cooks whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindExerciseCommand extends FindCommand {

    public static final String VARIANT_WORD = "exercise";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all exercises with names containing any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " bench crunches run";

    private final ExerciseNameContainsKeywordsPredicate predicate;

    public FindExerciseCommand(ExerciseNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredExerciseList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_EXERCISES_LISTED_OVERVIEW, model.getFilteredExerciseList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindExerciseCommand // instanceof handles nulls
                && predicate.equals(((FindExerciseCommand) other).predicate)); // state check
    }
}
