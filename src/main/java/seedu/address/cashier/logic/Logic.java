package seedu.address.cashier.logic;

import seedu.address.cashier.commands.CommandResult;
import seedu.address.cashier.util.InventoryList;

public interface Logic {

    CommandResult execute(String commandText) throws Exception;

    InventoryList getInventoryList() throws Exception;

}
