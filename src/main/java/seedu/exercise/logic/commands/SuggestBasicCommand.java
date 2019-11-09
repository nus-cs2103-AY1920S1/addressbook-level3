package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_SUGGEST_TYPE;
import static seedu.exercise.model.util.DefaultExerciseDatabaseUtil.getBasicExercises;

import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.Model;
import seedu.exercise.ui.ListResourceType;

//@@author kwekke
/**
 * Lists basic exercises in the exercise database to the user.
 */
public class SuggestBasicCommand extends SuggestCommand {

    public static final String MESSAGE_USAGE_SUGGEST_BASIC = "Parameters: "
            + PREFIX_SUGGEST_TYPE + "SUGGEST_TYPE"
            + "\t\tExample: " + COMMAND_WORD + " "
            + PREFIX_SUGGEST_TYPE + "basic";

    public static final String MESSAGE_SUCCESS = "Listed all suggested basic exercises.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.setSuggestions(getBasicExercises());
        return new CommandResult(MESSAGE_SUCCESS, ListResourceType.SUGGESTION);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SuggestBasicCommand); // instanceof handles nulls
    }
}
