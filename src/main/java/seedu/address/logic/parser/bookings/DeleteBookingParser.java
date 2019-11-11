package seedu.address.logic.parser.bookings;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.bookings.DeleteBookingCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the arguments to return a {@code DeleteExpenditureCommand}.
 */
public class DeleteBookingParser implements Parser<DeleteBookingCommand> {
    @Override
    public DeleteBookingCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteBookingCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteBookingCommand.MESSAGE_USAGE), pe);
        }
    }
}
