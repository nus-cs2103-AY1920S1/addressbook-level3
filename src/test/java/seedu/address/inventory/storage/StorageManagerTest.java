package seedu.address.inventory.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import seedu.address.inventory.model.exception.NoSuchIndexException;
import seedu.address.inventory.util.InventoryList;
import seedu.address.testutil.TypicalItem;

public class StorageManagerTest {
    @Test
    public void readAndWriteFileTest() {
        try {
            File tempFile = File.createTempFile("test", "inventoryStorage");
            Storage inventoryStorage = new StorageManager(tempFile);
            InventoryList inventoryList = new InventoryList();
            inventoryList.add(TypicalItem.FISH_BURGER);
            inventoryList.add(TypicalItem.PHONE_CASE);
            inventoryStorage.writeFile(inventoryList);

            InventoryList retrieved = inventoryStorage.getInventoryList();
            assertEquals(inventoryList, retrieved);
        } catch (IOException | NoSuchIndexException e) {
            throw new AssertionError("This method should not throw an exception.");
        } catch (Exception e) {
            fail();
        }
    }
}
