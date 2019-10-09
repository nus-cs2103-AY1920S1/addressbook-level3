package seedu.address.inventory.storage;

import seedu.address.inventory.util.InventoryList;

public interface Storage {

    public InventoryList getInventoryList() throws Exception;
}
