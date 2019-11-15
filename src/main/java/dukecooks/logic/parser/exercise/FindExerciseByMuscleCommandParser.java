package dukecooks.logic.parser.exercise;

import static dukecooks.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import dukecooks.logic.commands.exercise.FindExerciseByMuscleCommand;
import dukecooks.logic.commands.exercise.FindExerciseCommand;
import dukecooks.logic.parser.Parser;
import dukecooks.logic.parser.exceptions.ParseException;
import dukecooks.model.workout.exercise.components.MusclesTrainedContainsKeywordsPredicate;

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
