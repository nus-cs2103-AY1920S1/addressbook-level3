package seedu.address.stubs;

import java.util.ArrayList;

import seedu.address.inventory.logic.Logic;
import seedu.address.inventory.model.Item;
import seedu.address.inventory.util.InventoryList;
import seedu.address.util.CommandResult;


/**
 * Stub to test Overview statistics functions.
 */
public class InventoryLogicStubForOverview implements Logic {

    private InventoryList inventoryList;

    public InventoryLogicStubForOverview(InventoryList inventoryList) {
        this.inventoryList = inventoryList;
    }

    @Override
    public CommandResult execute(String commandText) {
        throw new AssertionError("This should not be called.");
    }

    @Override
    public InventoryList getInventoryList() {
        return inventoryList;
    }

    @Override
    public ArrayList<Item> getInventoryListInArrayList() {
        throw new AssertionError("This should not be called.");
    }

    @Override
    public void resetAndWriteIntoInventoryFile(InventoryList inventoryList) {
        throw new AssertionError("This should not be called.");
    }
}
