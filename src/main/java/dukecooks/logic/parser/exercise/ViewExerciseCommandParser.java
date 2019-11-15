package dukecooks.logic.parser.exercise;

import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.exercise.ViewExerciseCommand;
import dukecooks.logic.parser.Parser;
import dukecooks.logic.parser.ParserUtil;
import dukecooks.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewExerciseCommand object
 */
public class ViewExerciseCommandParser implements Parser<ViewExerciseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewExerciseCommand
     * and returns a ViewExerciseCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    @Override
    public ViewExerciseCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        Index targetIndex = ParserUtil.parseIndex(trimmedArgs);
        return new ViewExerciseCommand(targetIndex);
    }
}
