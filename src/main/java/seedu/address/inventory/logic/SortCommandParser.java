package seedu.address.inventory.logic;

import seedu.address.inventory.commands.SortCategoryCommand;
import seedu.address.inventory.commands.SortCommand;
import seedu.address.inventory.commands.SortDescriptionCommand;
import seedu.address.inventory.commands.SortQuantityCommand;
import seedu.address.inventory.logic.exception.NoSuchSortException;
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
        /*} else if (argsArr[1].equals("reset")) {
            return new SortResetCommand(); */
        } else {
            throw new NoSuchSortException(InventoryMessages.MESSAGE_NO_SUCH_SORT_COMMAND);
        }
    }
}
