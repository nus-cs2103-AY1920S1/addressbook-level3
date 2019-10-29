package seedu.weme.logic.parser.commandparser.memecommandparser;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.memecommand.MemeUnarchiveCommand;
import seedu.weme.logic.parser.Parser;
import seedu.weme.logic.parser.exceptions.ParseException;
import seedu.weme.logic.parser.util.ParserUtil;

/**
 * Parses input arguments and creates a new MemeUnarchiveCommand object
 */
public class MemeUnarchiveCommandParser implements Parser<MemeUnarchiveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MemeUnarchiveCommand
     * and returns a MemeUnarchiveCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MemeUnarchiveCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new MemeUnarchiveCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MemeUnarchiveCommand.MESSAGE_USAGE), pe);
        }
    }

}
