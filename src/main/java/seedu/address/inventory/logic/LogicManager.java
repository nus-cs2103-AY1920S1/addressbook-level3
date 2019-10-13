package seedu.address.inventory.logic;

import seedu.address.inventory.commands.Command;
import seedu.address.inventory.commands.CommandResult;
import seedu.address.inventory.util.InventoryList;

/**
 * Manages the logic behind the inventory tab.
 */
public class LogicManager implements Logic {
    private final seedu.address.cashier.model.ModelManager cashierManager;
    private final seedu.address.cashier.storage.StorageManager cashierStorage;
    private InventoryTabParser parser;
    private final seedu.address.inventory.model.Model inventoryModel;
    private final seedu.address.inventory.storage.Storage inventoryStorage;

    public LogicManager(seedu.address.cashier.model.ModelManager cashierManager,
                        seedu.address.cashier.storage.StorageManager cashierStorage,
                        seedu.address.inventory.model.Model inventoryModel,
                        seedu.address.inventory.storage.StorageManager inventoryStorage) {
        this.cashierManager = cashierManager;
        this.cashierStorage = cashierStorage;

        parser = new InventoryTabParser();

        this.inventoryModel = inventoryModel;
        this.inventoryStorage = inventoryStorage;
    }

    @Override
    public CommandResult execute(String commandText) throws Exception {
        Command command = parser.parseCommand(commandText,
                inventoryModel.getInventoryList().size());
        CommandResult commandResult = command.execute(inventoryModel);
        inventoryModel.updateIndexes();
        inventoryStorage.writeFile(inventoryModel.getInventoryList());
        return commandResult;
    }

    @Override
    public InventoryList getInventoryList() throws Exception {
        return inventoryModel.getInventoryList();
    }

    public InventoryList getInventoryListFromFile() throws Exception {
        return this.inventoryStorage.getInventoryList();
    }

    public void writeIntoInventoryFile() throws Exception {
        inventoryModel.writeInInventoryFile();
    }
}



