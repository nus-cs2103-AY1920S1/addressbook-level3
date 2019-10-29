package seedu.address.inventory.model;

import org.junit.jupiter.api.Test;
import seedu.address.inventory.model.exception.NoSuchIndexException;
import seedu.address.inventory.model.exception.NoSuchItemException;
import seedu.address.inventory.storage.Storage;
import seedu.address.inventory.storage.StorageManager;
import seedu.address.inventory.util.InventoryList;
import seedu.address.testutil.TypicalItem;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ModelManagerTest {
    @Test
    public void readInUpdatedListTest() {
        try {
            File tempFile = File.createTempFile("test", "inventoryModelManager");
            StorageManager inventoryStorage = new StorageManager(tempFile);
            Model inventoryModel1 = new ModelManager(inventoryStorage);
            Model inventoryModel2 = new ModelManager(inventoryStorage);
            inventoryModel1.addItem(TypicalItem.PHONE_CASE);
            inventoryModel1.addItem(TypicalItem.WATER);
            inventoryModel1.writeInInventoryFile();

            inventoryModel2.addItem(TypicalItem.BLACK_SHIRT);
            inventoryModel2.readInUpdatedList();
            assertTrue(inventoryModel1.equals(inventoryModel2));
        } catch (IOException e) {
            throw new AssertionError("This method should not throw an exception.");
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void hasItemInInventoryTest_unsuccessful() {
        Model inventoryModel = new ModelManager(new InventoryList());
        inventoryModel.addItem(TypicalItem.FISH_BURGER);

        try {
            assertFalse(inventoryModel.hasItemInInventory(TypicalItem.BLACK_SHIRT));
        } catch (Exception e) {
            fail();
        }
    }
}
