package seedu.address.logic.parser.exercise;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.exercise.FindExerciseByMuscleCommand;
import seedu.address.logic.commands.exercise.FindExerciseCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.exercise.components.MusclesTrainedContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindExerciseByMuscleCommand object
 */
public class FindExerciseByMuscleCommandParser implements Parser<FindExerciseByMuscleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindExerciseByMuscleCommand
     * and returns a FindExerciseByMuscleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindExerciseByMuscleCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindExerciseCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindExerciseByMuscleCommand(new MusclesTrainedContainsKeywordsPredicate(Arrays
                .asList(nameKeywords)));
    }

}
