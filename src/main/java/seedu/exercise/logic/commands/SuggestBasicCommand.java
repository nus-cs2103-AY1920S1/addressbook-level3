package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_SUGGEST_TYPE;
import static seedu.exercise.model.util.SampleDataUtil.getBasicExercises;

import java.util.Arrays;
import java.util.List;

import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.Model;
import seedu.exercise.model.resource.Exercise;

/**
 * Lists basic exercises in the exercise database to the user.
 */
public class SuggestBasicCommand extends SuggestCommand {

    public static final String MESSAGE_USAGE_SUGGEST_BASIC = "Parameters: "
            + PREFIX_SUGGEST_TYPE + "SUGGEST_TYPE" + "\n"
            + "\t\tExample: " + COMMAND_WORD + " "
            + PREFIX_SUGGEST_TYPE + "basic";

    public static final String MESSAGE_SUCCESS = "Listed all suggested basic exercises.";
    public static final String SUGGEST_TYPE = "basic";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Exercise> basicExercises = Arrays.asList(getBasicExercises());
        model.setSuggestions(basicExercises);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SuggestBasicCommand); // instanceof handles nulls
    }
}
