package dukecooks.logic.parser.workout;

import static dukecooks.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.workout.DeleteWorkoutCommand;
import dukecooks.logic.parser.exceptions.ParseException;
import dukecooks.logic.parser.exercise.WorkoutPlannerParserUtil;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteWorkoutCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteWorkoutCommand parse(String args) throws ParseException {
        try {
            Index index = WorkoutPlannerParserUtil.parseIndex(args);
            return new DeleteWorkoutCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(String
                    .format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteWorkoutCommand.MESSAGE_USAGE), pe);
        }
    }
}
