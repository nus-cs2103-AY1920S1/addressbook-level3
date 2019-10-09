package seedu.address.inventory.logic;

import seedu.address.inventory.commands.Command;
import seedu.address.inventory.commands.CommandResult;
import seedu.address.inventory.model.ModelManager;
import seedu.address.inventory.storage.StorageManager;
import seedu.address.inventory.util.InventoryList;
import seedu.address.inventory.model.Item;

import java.util.ArrayList;

public class LogicManager implements Logic {

    //private final Model model;
    private final seedu.address.cashier.model.ModelManager cashierManager;
    private final seedu.address.cashier.storage.StorageManager cashierStorage;
    private InventoryTabParser parser;
    private final seedu.address.person.storage.Storage personStorage;
    private final seedu.address.person.model.Model personModel;
    private final seedu.address.reimbursement.model.Model reimbursementModel;
    private final seedu.address.reimbursement.storage.Storage reimbursementStorage;
    private final seedu.address.transaction.model.Model transactionModel;
    private final seedu.address.transaction.storage.Storage transactionStorage;
    private final seedu.address.inventory.model.Model inventoryModel;
    private final seedu.address.inventory.storage.Storage inventoryStorage;

    //Model inventoryModel,
    public LogicManager(seedu.address.cashier.model.ModelManager cashierManager,
                        seedu.address.cashier.storage.StorageManager cashierStorage,
                        seedu.address.person.model.Model personModel,
                        seedu.address.person.storage.Storage personStorage,
                        seedu.address.reimbursement.model.Model reimbursementModel,
                        seedu.address.reimbursement.storage.Storage reimbursementStorage,
                        seedu.address.transaction.model.Model transactionModel,
                        seedu.address.transaction.storage.Storage transactionStorage,
                        seedu.address.inventory.model.Model inventoryModel,
                        seedu.address.inventory.storage.StorageManager inventoryStorage) {
        //this.model = inventoryModel;
        this.cashierManager = cashierManager;
        this.cashierStorage = cashierStorage;

        parser = new InventoryTabParser();

        this.personModel = personModel;
        this.personStorage = personStorage;

        this.reimbursementModel = reimbursementModel;
        this.reimbursementStorage = reimbursementStorage;

        this.transactionModel = transactionModel;
        this.transactionStorage = transactionStorage;

        this.inventoryModel = inventoryModel;
        this.inventoryStorage = inventoryStorage;
    }

    @Override
    public CommandResult execute(String commandText) throws Exception {
        return null;
    }

    @Override
    public InventoryList getInventoryList() throws Exception {
        return null;
    }

    public InventoryList getInventoryListFromFile() throws Exception {
        return this.inventoryStorage.getInventoryList();
    }

    public void writeIntoInventoryFile() throws Exception {
        inventoryModel.writeInInventoryFile();
    }
}



