package dukecooks.logic.parser.workout;

import static dukecooks.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.workout.RunWorkoutCommand;
import dukecooks.logic.parser.Parser;
import dukecooks.logic.parser.exceptions.ParseException;
import dukecooks.logic.parser.exercise.WorkoutPlannerParserUtil;

/**
 * Parses input arguments and creates a new RunWorkoutCommand object
 */
public class RunWorkoutCommandParser implements Parser<RunWorkoutCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RunCommand
     * and returns an RunWorkoutCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RunWorkoutCommand parse(String args) throws ParseException {
        try {
            Index index = WorkoutPlannerParserUtil.parseIndex(args);
            return new RunWorkoutCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RunWorkoutCommand.MESSAGE_USAGE), pe);
        }
    }
}
