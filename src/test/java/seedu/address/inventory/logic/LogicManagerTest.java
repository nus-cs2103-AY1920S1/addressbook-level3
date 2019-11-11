package seedu.address.inventory.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import seedu.address.inventory.model.ModelManager;
import seedu.address.inventory.storage.StorageManager;
import seedu.address.inventory.ui.InventoryMessages;
import seedu.address.inventory.util.InventoryList;
import seedu.address.testutil.TypicalItem;
import seedu.address.util.CommandResult;

public class LogicManagerTest {
    @Test
    public void execute_command_successful() {
        try {
            File tempFile = File.createTempFile("testingLogic", "tempInventory.txt");
            ModelManager inventoryModel = new ModelManager(new InventoryList());
            StorageManager inventoryStorage = new StorageManager(tempFile);
            InventoryList inventoryList = inventoryStorage.getInventoryList();
            inventoryList.add(TypicalItem.FISH_BURGER);
            inventoryList.add(TypicalItem.BLACK_SHIRT);
            inventoryList.add(TypicalItem.CHIPS);
            Logic logic = new LogicManager(inventoryModel, inventoryStorage);

            CommandResult commandResult = null;
            try {
                commandResult = logic.execute("sort description");
            } catch (Exception e) {
                fail();
            }
            assertEquals(new CommandResult(InventoryMessages.MESSAGE_SORTED_BY_DESCRIPTION), commandResult);
        } catch (IOException e) {
            throw new AssertionError("This method should not throw an exception.");
        }
    }
}
