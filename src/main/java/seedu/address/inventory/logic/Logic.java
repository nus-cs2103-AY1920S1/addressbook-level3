package seedu.address.inventory.logic;

import seedu.address.inventory.commands.CommandResult;
import seedu.address.inventory.util.InventoryList;

public interface Logic {

    CommandResult execute(String commandText) throws Exception;

    InventoryList getInventoryList() throws Exception;
}
