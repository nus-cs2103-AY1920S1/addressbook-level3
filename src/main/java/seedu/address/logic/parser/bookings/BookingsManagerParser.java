package seedu.address.logic.parser.bookings;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_TYPE;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.bookings.DeleteBookingCommand;
import seedu.address.logic.commands.bookings.EnterCreateBookingCommand;
import seedu.address.logic.commands.bookings.EnterEditBookingCommand;
import seedu.address.logic.parser.PageParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.navbar.NavbarViewParser;

/**
 * Parses commands related to the expense manager page.
 */
public class BookingsManagerParser implements PageParser {
    private static final String MESSAGE_COMMAND_TYPES = " Available command types: \n"
            + EnterCreateBookingCommand.COMMAND_WORD + " "
            + DeleteBookingCommand.COMMAND_WORD + " "
            + EnterEditBookingCommand.COMMAND_WORD + " | "
            + NavbarViewParser.MESSAGE_COMMAND_TYPES;

    @Override
    public Command parse(String command, String arguments) throws ParseException {
        BookingsManagerCommand commandType;
        try {
            commandType = BookingsManagerCommand.valueOf(command);
        } catch (IllegalArgumentException ex) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }

        switch (commandType) {
        case CREATE:
            return new EnterCreateBookingParser().parse(arguments);
        case DELETE:
            return new DeleteBookingParser().parse(arguments);
        case EDIT:
            return new EnterEditBookingParser().parse(arguments);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }
    }
}
