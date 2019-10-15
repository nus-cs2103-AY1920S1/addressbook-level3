package seedu.address.inventory.logic;

import seedu.address.inventory.commands.CommandResult;
import seedu.address.inventory.util.InventoryList;

/**
 * API of the Logic component
 */
public interface Logic {

    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws Exception If an error occurs during command execution.
     */
    CommandResult execute(String commandText) throws Exception;

    /**
     * Returns the inventory list in the model manager.
     * @return Inventory List in the model manager.
     */
    InventoryList getInventoryList() throws Exception;
}
