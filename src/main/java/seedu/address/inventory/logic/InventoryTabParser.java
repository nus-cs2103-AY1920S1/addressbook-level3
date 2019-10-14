package seedu.address.inventory.logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.inventory.commands.AddCommand;
import seedu.address.inventory.commands.Command;
import seedu.address.inventory.commands.EditCommand;
import seedu.address.inventory.commands.SortCommand;
import seedu.address.inventory.logic.exception.ParseException;
import seedu.address.inventory.ui.InventoryMessages;
import seedu.address.transaction.commands.DeleteIndexCommand;

/**
 * Parses user inputs to the Inventory Tab.
 */
public class InventoryTabParser {
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws Exception if the user input does not conform to the expected format.
     * */
    public Command parseCommand(String userInput, int inventoryListSize) throws Exception {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(InventoryMessages.MESSAGE_INVALID_ADD_COMMAND_FORMAT);
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments, inventoryListSize);


        case DeleteIndexCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        default:
            throw new ParseException(InventoryMessages.NO_SUCH_COMMAND);
        }
    }
}
