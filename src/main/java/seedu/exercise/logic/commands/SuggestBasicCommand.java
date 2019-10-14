package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_SUGGEST;
import static seedu.exercise.model.Model.PREDICATE_SHOW_ALL_EXERCISES;

import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.Model;

/**
 * Lists basic exercises in the exercise database to the user.
 */
public class SuggestBasicCommand extends SuggestCommand {

    public static final String MESSAGE_SUCCESS = "Listed all suggested basic exercises.";

    public static final String MESSAGE_USAGE_SUGGEST_BASIC = "Parameters: "
            + PREFIX_SUGGEST + "SUGGEST TYPE ";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateSuggestedExerciseList(PREDICATE_SHOW_ALL_EXERCISES);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SuggestBasicCommand); // instanceof handles nulls
    }
}
