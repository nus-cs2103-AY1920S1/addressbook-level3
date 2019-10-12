package seedu.address.logic.parser.trips;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_TYPE;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.trips.DeleteTripCommand;
import seedu.address.logic.commands.trips.EnterCreateTripCommand;
import seedu.address.logic.commands.trips.EnterEditTripCommand;
import seedu.address.logic.commands.trips.EnterTripCommand;
import seedu.address.logic.parser.PageParser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses commands related to the trip manager page.
 */
public class TripManagerParser implements PageParser {
    private static final String MESSAGE_COMMAND_TYPES = " Available command types: \n"
            + EnterCreateTripCommand.COMMAND_WORD + " "
            + DeleteTripCommand.COMMAND_WORD + " "
            + EnterTripCommand.COMMAND_WORD + " "
            + EnterEditTripCommand.COMMAND_WORD;

    @Override
    public Command parse(String command, String arguments) throws ParseException {
        TripManagerCommand commandType;
        try {
            commandType = TripManagerCommand.valueOf(command);
        } catch (IllegalArgumentException ex) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }

        switch (commandType) {
        case CREATE:
            return new EnterCreateTripParser().parse(arguments);
        case DELETE:
            return new DeleteTripParser().parse(arguments);
        case GOTO:
            return new EnterTripParser().parse(arguments);
        case EDIT:
            return new EnterEditTripParser().parse(arguments);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }
    }
}
