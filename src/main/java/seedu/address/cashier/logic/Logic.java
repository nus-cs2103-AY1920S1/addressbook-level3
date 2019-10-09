package seedu.address.cashier.logic;

import java.util.ArrayList;

import seedu.address.cashier.commands.CommandResult;
import seedu.address.cashier.util.InventoryList;
import seedu.address.inventory.model.Item;

public interface Logic {

    CommandResult execute(String commandText) throws Exception;

    InventoryList getInventoryList() throws Exception;

    ArrayList<Item> getSalesList() throws Exception;

}
