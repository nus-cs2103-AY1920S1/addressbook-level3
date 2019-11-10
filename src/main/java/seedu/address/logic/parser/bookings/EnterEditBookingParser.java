package seedu.address.logic.parser.bookings;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.bookings.EnterEditBookingCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the arguments to return a {@code EnterEditExpenditureCommand}.
 */
public class EnterEditBookingParser implements Parser<EnterEditBookingCommand> {
    @Override
    public EnterEditBookingCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new EnterEditBookingCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EnterEditBookingCommand.MESSAGE_USAGE), pe);
        }
    }
}
