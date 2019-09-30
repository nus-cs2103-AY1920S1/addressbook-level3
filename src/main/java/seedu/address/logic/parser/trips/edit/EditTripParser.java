package seedu.address.logic.parser.trips.edit;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.trips.DeleteTripCommand;
import seedu.address.logic.commands.trips.EnterCreateTripCommand;
import seedu.address.logic.commands.trips.EnterTripCommand;
import seedu.address.logic.parser.PageParser;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_TYPE;

public class EditTripParser implements PageParser {
    private static final String MESSAGE_COMMAND_TYPES = " Available command types: \n"
            + EnterCreateTripCommand.COMMAND_WORD
            + DeleteTripCommand.COMMAND_WORD
            + EnterTripCommand.COMMAND_WORD;

    @Override
    public Command parse(String command, String arguments) throws ParseException {
        EditTripCommand commandType;
        try {
            commandType = EditTripCommand.valueOf(command);
        } catch (IllegalArgumentException ex) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }

        switch (commandType) {
        case NAME:
        case FROM:
        case TO:
        case LOCATION:
        case BUDGET:
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }
    }
}
