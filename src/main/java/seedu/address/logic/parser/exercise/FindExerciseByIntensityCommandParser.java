package seedu.address.logic.parser.exercise;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.exercise.FindExerciseByIntensityCommand;
import seedu.address.logic.commands.exercise.FindExerciseCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.exercise.components.IntensityContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindExerciseByIntensityCommand object
 */
public class FindExerciseByIntensityCommandParser implements Parser<FindExerciseByIntensityCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindExerciseByIntensityCommand
     * and returns a FindExerciseByIntensityCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindExerciseByIntensityCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindExerciseCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindExerciseByIntensityCommand(new IntensityContainsKeywordsPredicate(Arrays
                .asList(nameKeywords)));
    }

}
