package seedu.address.logic.parser.inventory;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_TYPE;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.inventory.AddInventoryCommand;
import seedu.address.logic.commands.inventory.DeleteInventoryCommand;
import seedu.address.logic.parser.PageParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.navbar.NavbarViewParser;

/**
 * Parses the commands related to the itinerary view page.
 */
public class InventoryViewParser implements PageParser<Command> {
    static final String MESSAGE_COMMAND_TYPES = " Available command types: \n"
            + AddInventoryCommand.COMMAND_WORD + " "
            + DeleteInventoryCommand.COMMAND_WORD + " | "
            + NavbarViewParser.MESSAGE_COMMAND_TYPES;
    @Override
    public Command parse(String command, String arguments) throws ParseException {

        InventoryViewCommand commandType;
        try {
            commandType = InventoryViewCommand.valueOf(command);
        } catch (IllegalArgumentException ex) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }

        switch (commandType) {
        case ADD:
            return new AddInventoryParser().parse(arguments);
        case DELETE:
            return new DeleteInventoryParser().parse(arguments);
        case CHECK:
            return new CheckInventoryParser().parse(arguments);
        case UNCHECK:
            return new UncheckInventoryParser().parse(arguments);

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }
    }
}
