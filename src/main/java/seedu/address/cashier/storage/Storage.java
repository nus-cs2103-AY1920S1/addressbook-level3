package seedu.address.cashier.storage;

import seedu.address.cashier.util.InventoryList;

/**
 * API of the Storage component
 */
public interface Storage {

    /**
     * Reads in the inventory list from the specified text file in Storage.
     * @return Inventory List read.
     */
    public InventoryList getInventoryList() throws Exception;

}
