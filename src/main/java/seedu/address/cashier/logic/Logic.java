package seedu.address.cashier.logic;

import java.util.ArrayList;

import seedu.address.cashier.logic.commands.exception.NoCashierFoundException;
import seedu.address.cashier.model.exception.AmountExceededException;
import seedu.address.cashier.util.InventoryList;
import seedu.address.inventory.model.Item;
import seedu.address.util.CommandResult;

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

    /**
     * Returns the sales list in the model manager.
     * @return Sales List in the model manager.
     */
    ArrayList<Item> getSalesList() throws Exception;

    void writeInInventoryFile() throws Exception;

    void readInUpdatedList() throws Exception;

    String getAmount() throws AmountExceededException;

    String getCashier() throws NoCashierFoundException;

}
