package seedu.address.inventory.logic.parser;

import static seedu.address.cashier.model.ModelManager.onCashierMode;
import static seedu.address.inventory.ui.InventoryMessages.MESSAGE_ON_CASHIER_MODE;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.inventory.logic.commands.AddCommand;
import seedu.address.inventory.logic.commands.Command;
import seedu.address.inventory.logic.commands.EditCommand;
import seedu.address.inventory.logic.commands.SortCommand;
import seedu.address.inventory.logic.commands.exception.NoSuchSortException;
import seedu.address.inventory.logic.commands.exception.NotANumberException;
import seedu.address.inventory.logic.parser.exception.OnCashierModeException;
import seedu.address.inventory.logic.parser.exception.ParseException;
import seedu.address.inventory.model.exception.NoSuchItemException;
import seedu.address.inventory.ui.InventoryMessages;
import seedu.address.inventory.util.InventoryList;
import seedu.address.transaction.logic.commands.DeleteIndexCommand;

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
     * @throws ParseException if the user input does not conform to the expected format.
     * @throws NotANumberException if the user input is a String when a number is expected.
     * @throws NoSuchSortException if the user input is a SortCommand that does not sort by the existing categories.
     */
    public Command parseCommand(String userInput, InventoryList inventoryList) throws ParseException,
            NotANumberException, NoSuchSortException, NoSuchItemException, OnCashierModeException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(InventoryMessages.MESSAGE_NO_COMMAND);
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            try {
                return new AddCommandParser().parse(arguments, inventoryList);
            } catch (NoSuchItemException e) {
                throw new NoSuchItemException(InventoryMessages.NO_SUCH_ITEM_INVENTORY);
            }

        case DeleteIndexCommand.COMMAND_WORD:
            if (onCashierMode) {
                throw new OnCashierModeException(MESSAGE_ON_CASHIER_MODE);
            }
            return new DeleteCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            if (onCashierMode) {
                throw new OnCashierModeException(MESSAGE_ON_CASHIER_MODE);
            }
            return new EditCommandParser().parse(arguments);

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        default:
            throw new ParseException(InventoryMessages.MESSAGE_NO_COMMAND);
        }
    }
}
