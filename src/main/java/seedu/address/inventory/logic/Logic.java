package seedu.address.inventory.logic;

import java.io.IOException;
import java.util.ArrayList;

import seedu.address.inventory.logic.commands.exception.CommandException;
import seedu.address.inventory.logic.commands.exception.NoSuchSortException;
import seedu.address.inventory.logic.commands.exception.NotANumberException;
import seedu.address.inventory.logic.parser.exception.InvalidNumberException;
import seedu.address.inventory.logic.parser.exception.OnCashierModeException;
import seedu.address.inventory.logic.parser.exception.ParseException;
import seedu.address.inventory.model.Item;
import seedu.address.inventory.model.exception.NoSuchIndexException;
import seedu.address.inventory.model.exception.NoSuchItemException;
import seedu.address.inventory.util.InventoryList;
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
    CommandResult execute(String commandText) throws IOException, OnCashierModeException, ParseException,
            NoSuchItemException, NotANumberException, NoSuchSortException, InvalidNumberException, NoSuchIndexException,
            CommandException;

    /**
     * Returns the inventory list in the model manager.
     * @return Inventory List in the model manager.
     */
    InventoryList getInventoryList();

    /**
     * Returns the inventory list in the form of array list.
     * @return the inventory list in the form of array list.
     */
    ArrayList<Item> getInventoryListInArrayList();

    void resetAndWriteIntoInventoryFile(InventoryList inventoryList) throws IOException, NoSuchIndexException;
}
