package seedu.address.inventory.logic;

import java.util.ArrayList;

import seedu.address.inventory.logic.commands.Command;
import seedu.address.inventory.logic.parser.InventoryTabParser;
import seedu.address.inventory.model.Item;
import seedu.address.inventory.model.Model;
import seedu.address.inventory.model.ModelManager;
import seedu.address.inventory.util.InventoryList;
import seedu.address.util.CommandResult;

/**
 * Manages the logic behind the inventory tab.
 */
public class LogicManager implements Logic {

    private InventoryTabParser parser;
    private Model inventoryModel;
    private final seedu.address.inventory.storage.Storage inventoryStorage;

    public LogicManager(Model inventoryModel,
                        seedu.address.inventory.storage.Storage inventoryStorage) {
        parser = new InventoryTabParser();

        this.inventoryModel = inventoryModel;
        this.inventoryStorage = inventoryStorage;
    }

    @Override
    public CommandResult execute(String commandText) throws Exception {
        Command command = parser.parseCommand(commandText, inventoryModel.getInventoryList());
        CommandResult commandResult = command.execute(inventoryModel);
        inventoryModel.updateIndexes();
        inventoryStorage.writeFile(inventoryModel.getInventoryList());
        return commandResult;
    }

    @Override
    public InventoryList getInventoryList() {
        return inventoryModel.getInventoryList();
    }

    @Override
    public ArrayList<Item> getInventoryListInArrayList() {
        ArrayList<Item> inventoryList = inventoryModel.getInventoryListInArrayList();
        return inventoryList;
    }

    @Override
    public void resetAndWriteIntoInventoryFile(InventoryList inventoryList) throws Exception {
        this.inventoryModel = new ModelManager(inventoryList);
        inventoryStorage.writeFile(inventoryList);
    }
}



