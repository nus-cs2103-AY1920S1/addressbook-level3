package dukecooks.logic.parser.exercise;

import static dukecooks.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.exercise.DeleteExerciseCommand;
import dukecooks.logic.parser.Parser;
import dukecooks.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteExerciseCommandParser implements Parser<DeleteExerciseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteExerciseCommand parse(String args) throws ParseException {
        try {
            Index index = WorkoutPlannerParserUtil.parseIndex(args);
            return new DeleteExerciseCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteExerciseCommand.MESSAGE_USAGE), pe);
        }
    }

}
