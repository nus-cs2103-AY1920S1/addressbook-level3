package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_MUSCLE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_OPERATION_TYPE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_SUGGEST_TYPE;

import java.util.function.Predicate;

import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.Model;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.ui.ListResourceType;

/**
 * Lists possible exercises to the user.
 */
public class SuggestPossibleCommand extends SuggestCommand {

    public static final String MESSAGE_USAGE_SUGGEST_POSSIBLE = "Parameters: "
            + PREFIX_SUGGEST_TYPE + "SUGGEST_TYPE "
            + PREFIX_OPERATION_TYPE + "OPERATION_TYPE"
            + "[" + PREFIX_MUSCLE + "MUSCLE] "
            + "[" + "CUSTOM_PROPERTY_PREFIX" + "/" + "VALUE]"
            + "\t\tExample: " + COMMAND_WORD + " "
            + PREFIX_SUGGEST_TYPE + "possible "
            + PREFIX_OPERATION_TYPE + "and "
            + PREFIX_MUSCLE + "Legs";

    public static final String MESSAGE_SUCCESS = "Listed suggested exercises.";

    private Predicate<Exercise> predicate;

    public SuggestPossibleCommand(Predicate<Exercise> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateSuggestedExerciseList(predicate);
        return new CommandResult(MESSAGE_SUCCESS, ListResourceType.SUGGESTION);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof SuggestPossibleCommand) // instanceof handles nulls
            && predicate.equals(((SuggestPossibleCommand) other).predicate);
    }
}
