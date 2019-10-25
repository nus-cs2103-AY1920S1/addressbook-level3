package dukecooks.logic.parser.diary;

import static dukecooks.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.diary.DeleteDiaryCommand;
import dukecooks.logic.parser.Parser;
import dukecooks.logic.parser.ParserUtil;
import dukecooks.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteDiaryCommand object
 */
public class DeleteDiaryCommandParser implements Parser<DeleteDiaryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteDiaryCommand
     * and returns a DeleteDiaryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteDiaryCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteDiaryCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteDiaryCommand.MESSAGE_USAGE), pe);
        }
    }

}
