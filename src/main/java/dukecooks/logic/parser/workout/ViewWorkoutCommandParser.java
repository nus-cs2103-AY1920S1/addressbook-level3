package dukecooks.logic.parser.workout;

import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.workout.ViewWorkoutCommand;
import dukecooks.logic.parser.Parser;
import dukecooks.logic.parser.ParserUtil;
import dukecooks.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewWorkoutCommand object
 */
public class ViewWorkoutCommandParser implements Parser<ViewWorkoutCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewWorkoutCommand
     * and returns a ViewDiaryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    @Override
    public ViewWorkoutCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        Index targetIndex = ParserUtil.parseIndex(trimmedArgs);
        return new ViewWorkoutCommand(targetIndex);
    }
}
