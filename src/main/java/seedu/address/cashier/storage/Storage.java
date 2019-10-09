package seedu.address.cashier.storage;

import seedu.address.cashier.util.InventoryList;

public interface Storage {

    public InventoryList getInventoryList() throws Exception;

}
