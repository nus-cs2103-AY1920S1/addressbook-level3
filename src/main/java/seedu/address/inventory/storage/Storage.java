package seedu.address.inventory.storage;

import java.io.IOException;

import seedu.address.inventory.model.exception.NoSuchIndexException;
import seedu.address.inventory.util.InventoryList;

/**
 * API of the Storage component
 */
public interface Storage {

    public InventoryList getInventoryList() throws IOException;

    public void writeFile(InventoryList inventoryList) throws IOException, NoSuchIndexException;
}
