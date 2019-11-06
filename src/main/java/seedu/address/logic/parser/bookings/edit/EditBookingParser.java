package seedu.address.logic.parser.bookings.edit;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_TYPE;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.bookings.edit.CancelEditBookingsCommand;
import seedu.address.logic.commands.bookings.edit.DoneEditBookingsCommand;
import seedu.address.logic.commands.bookings.edit.EditBookingsFieldCommand;
import seedu.address.logic.parser.PageParser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Placeholder javadoc.
 */
public class EditBookingParser implements PageParser {
    private static final String MESSAGE_COMMAND_TYPES = " Available command types: \n"
            + EditBookingsFieldCommand.COMMAND_WORD + " "
            + DoneEditBookingsCommand.COMMAND_WORD + " "
            + CancelEditBookingsCommand.COMMAND_WORD;

    @Override
    public Command parse(String command, String arguments) throws ParseException {
        EditBookingCommand commandType;
        try {
            commandType = EditBookingCommand.valueOf(command);
        } catch (IllegalArgumentException ex) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }

        switch (commandType) {
        case EDIT:
            return new EditBookingFieldParser().parse(arguments);
        case DONE:
            return new DoneEditBookingParser().parse(arguments);
        case CANCEL:
            return new CancelEditBookingParser().parse(arguments);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }
    }

}
