package seedu.deliverymans.logic.parser.universal;

import static seedu.deliverymans.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.deliverymans.commons.core.index.Index;
import seedu.deliverymans.logic.commands.universal.UndoTillCommand;
import seedu.deliverymans.logic.parser.Parser;
import seedu.deliverymans.logic.parser.ParserUtil;
import seedu.deliverymans.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UndoTillCommand object
 */
public class UndoTillCommandParser implements Parser<UndoTillCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the UndoTillCommand
     * and returns an UndoTillCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public UndoTillCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new UndoTillCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoTillCommand.MESSAGE_USAGE), pe);
        }
    }

}
