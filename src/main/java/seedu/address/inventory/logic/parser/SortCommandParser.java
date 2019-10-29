package seedu.address.inventory.logic.parser;

import seedu.address.inventory.logic.commands.SortCategoryCommand;
import seedu.address.inventory.logic.commands.SortCommand;
import seedu.address.inventory.logic.commands.SortDescriptionCommand;
import seedu.address.inventory.logic.commands.SortQuantityCommand;
import seedu.address.inventory.logic.commands.SortResetCommand;
import seedu.address.inventory.logic.commands.exception.NoSuchSortException;
import seedu.address.inventory.ui.InventoryMessages;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws NoSuchSortException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws NoSuchSortException {
        String[] argsArr = args.split(" ");
        if (argsArr[1].equals("quantity")) {
            return new SortQuantityCommand();
        } else if (argsArr[1].equals("description")) {
            return new SortDescriptionCommand();
        } else if (argsArr[1].equals("category")) {
            return new SortCategoryCommand();
        } else if (argsArr[1].equals("reset")) {
            return new SortResetCommand();
        } else {
            throw new NoSuchSortException(InventoryMessages.MESSAGE_NO_SUCH_SORT_COMMAND);
        }
    }
}
