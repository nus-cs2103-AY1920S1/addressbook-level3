package dukecooks.logic.parser.diary;

import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.diary.ViewDiaryCommand;
import dukecooks.logic.parser.Parser;
import dukecooks.logic.parser.ParserUtil;
import dukecooks.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewDiaryCommand object
 */
public class ViewDiaryCommandParser implements Parser<ViewDiaryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewDiaryCommand
     * and returns a ViewDiaryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewDiaryCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        Index targetIndex = ParserUtil.parseIndex(trimmedArgs);

        return new ViewDiaryCommand(targetIndex);
    }

}
