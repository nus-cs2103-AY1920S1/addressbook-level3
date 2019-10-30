package seedu.address.stubs;

import seedu.address.inventory.logic.Logic;
import seedu.address.inventory.logic.commands.CommandResult;
import seedu.address.inventory.util.InventoryList;

public class InventoryLogicStubForOverview implements Logic {

    private InventoryList inventoryList;

    public InventoryLogicStubForOverview(InventoryList inventoryList) {
        this.inventoryList = inventoryList;
    }

    @Override
    public CommandResult execute(String commandText) throws Exception {
        throw new AssertionError("This should not be called.");
    }

    @Override
    public InventoryList getInventoryList() {
        return inventoryList;
    }
}
