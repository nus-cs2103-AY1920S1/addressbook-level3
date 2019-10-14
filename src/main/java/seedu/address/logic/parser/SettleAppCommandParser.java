package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CancelAppCommand;
import seedu.address.logic.commands.SettleAppCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new SettleAppCommand object
 */
public class SettleAppCommandParser implements Parser<SettleAppCommand> {
    public SettleAppCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SettleAppCommand(index);
        } catch (ParseException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SettleAppCommand.MESSAGE_USAGE), e);
        }
    }
}
