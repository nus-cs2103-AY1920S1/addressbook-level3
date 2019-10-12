package seedu.address.inventory.storage;

import seedu.address.inventory.model.exception.NoSuchIndexException;
import seedu.address.inventory.util.InventoryList;

import java.io.IOException;

public interface Storage {

    public InventoryList getInventoryList() throws Exception;

    public void writeFile(InventoryList inventoryList) throws IOException, NoSuchIndexException;
}
