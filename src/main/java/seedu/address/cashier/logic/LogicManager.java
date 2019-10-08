package seedu.address.cashier.logic;

import seedu.address.cashier.commands.Command;
import seedu.address.cashier.commands.CommandResult;
import seedu.address.cashier.model.Model;
import seedu.address.cashier.storage.StorageManager;
import seedu.address.cashier.util.InventoryList;

public class LogicManager {

    private final Model model;
    private final StorageManager storage;
    private CashierTabParser parser;
    private final seedu.address.person.storage.Storage personStorage;
    private final seedu.address.person.model.Model personModel;
    private final seedu.address.reimbursement.model.Model reimbursementModel;
    private final seedu.address.reimbursement.storage.Storage reimbursementStorage;

    public LogicManager(Model inventoryModel, StorageManager cashierStorage,
                        seedu.address.person.model.Model personModel,
                        seedu.address.person.storage.Storage personStorage,
                        seedu.address.reimbursement.model.Model reimbursementModel,
                        seedu.address.reimbursement.storage.Storage reimbursementStorage) {
        this.model = inventoryModel;
        this.storage = cashierStorage;
        parser = new CashierTabParser();
        this.personModel = personModel;
        this.personStorage = personStorage;
        this.reimbursementModel = reimbursementModel;
        this.reimbursementStorage = reimbursementStorage;
    }

    @Override
    public CommandResult execute(String commandText) throws Exception {
        Command command = parser.parseCommand(commandText,
                model.getInventoryList().size());
        CommandResult commandResult = command.execute(model, personModel);
        model.updateIndexes();
        personStorage.saveAddressBook(personModel.getAddressBook());
        storage.writeFile(model.getInventoryList());
        //reimbursementModel.updateReimbursementList(model.getTransactionList());
        //reimbursementStorage.writeFile(reimbursementModel.getReimbursementList());
        return commandResult;
    }

    public InventoryList getInventoryListFromFile() throws Exception {
        return this.storage.getInventoryList();
    }

    public void writeIntoInventoryFile() throws Exception {
        //model.writeInInventoryFile();
        storage.writeFileToInventory(model.getInventoryList());
    }

    public void setTransaction(Transaction transaction, Transaction newTransaction) throws Exception {
        model.setTransaction(transaction, newTransaction);
    }

    public InventoryList getInventoryList() {
        return model.getInventoryList();
    }
}
