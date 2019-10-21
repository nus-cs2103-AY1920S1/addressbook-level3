package seedu.address.cashier.logic;

import java.util.ArrayList;

import seedu.address.cashier.commands.Command;
import seedu.address.cashier.commands.CommandResult;
import seedu.address.cashier.logic.exception.NoCashierFoundException;
import seedu.address.cashier.model.Model;
import seedu.address.cashier.storage.StorageManager;
import seedu.address.cashier.util.InventoryList;
import seedu.address.inventory.model.Item;

/**
 * The main LogicManager of the cashier tab.
 */
public class LogicManager implements Logic {

    //private final Model model;
    private final Model model;
    private final StorageManager storage;
    private CashierTabParser parser;
    private final seedu.address.person.model.Model personModel;
    private final seedu.address.transaction.model.Model transactionModel;
    private final seedu.address.transaction.storage.Storage transactionStorage;
    private final seedu.address.inventory.model.Model inventoryModel;
    private final seedu.address.inventory.storage.Storage inventoryStorage;

    //Model inventoryModel,
    public LogicManager(Model cashierManager,
                        StorageManager cashierStorage,
                        seedu.address.person.model.Model personModel,
                        seedu.address.transaction.model.Model transactionModel,
                        seedu.address.transaction.storage.Storage transactionStorage,
                        seedu.address.inventory.model.Model inventoryModel,
                        seedu.address.inventory.storage.Storage inventoryStorage) {
        //this.model = inventoryModel;
        this.model = cashierManager;
        this.storage = cashierStorage;

        parser = new CashierTabParser();

        this.personModel = personModel;

        this.transactionModel = transactionModel;
        this.transactionStorage = transactionStorage;

        this.inventoryModel = inventoryModel;
        this.inventoryStorage = inventoryStorage;
    }

    @Override
    public CommandResult execute(String commandText) throws Exception {
        Command command = parser.parseCommand(commandText, model, personModel);
        CommandResult commandResult = command.execute(model, personModel,
                transactionModel, inventoryModel);
        return commandResult;
    }

    public InventoryList getInventoryListFromFile() throws Exception {
        return this.storage.getInventoryList();
    }

    public void writeIntoInventoryFile() throws Exception {
        model.writeInInventoryFile();
    }

    public InventoryList getInventoryList() {
        return model.getInventoryList();
    }

    public ArrayList<Item> getSalesList() {
        return model.getSalesList();
    }

    @Override
    public String getAmount() {
        return String.valueOf(model.getTotalAmount());
    }

    @Override
    public String getCashier() throws NoCashierFoundException {
        try {
            model.getCashier();
        } catch (NoCashierFoundException e) {
            return "";
        }
        return String.valueOf(model.getCashier().getName());
    }

}



