package seedu.address.transaction.logic;

import seedu.address.transaction.commands.Command;
import seedu.address.transaction.commands.CommandResult;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.storage.StorageManager;
import seedu.address.transaction.util.TransactionList;

public class LogicManager implements Logic {

    private final Model model;
    private final StorageManager storage;
    private TransactionTabParser parser;
    private final seedu.address.person.storage.Storage personStorage;
    private final seedu.address.person.model.Model personModel;
    private final seedu.address.reimbursement.model.Model reimbursementModel;
    private final seedu.address.reimbursement.storage.Storage reimbursementStorage;

    public LogicManager(Model transactionModel, StorageManager transactionStorage,
                        seedu.address.person.model.Model personModel,
                        seedu.address.person.storage.Storage personStorage,
                        seedu.address.reimbursement.model.Model reimbursementModel,
                        seedu.address.reimbursement.storage.Storage reimbursementStorage) {
        this.model = transactionModel;
        this.storage = transactionStorage;
        parser = new TransactionTabParser();
        this.personStorage = personStorage;
        this.personModel = personModel;
        this.reimbursementModel = reimbursementModel;
        this.reimbursementStorage = reimbursementStorage;
    }

    @Override
    public CommandResult execute(String commandText) throws Exception {
        Command command = parser.parseCommand(commandText,
                model.getTransactionList().size(), personModel);
        CommandResult commandResult = command.execute(model, personModel);
        model.updateIndexes();
        personStorage.saveAddressBook(personModel.getAddressBook());
        storage.writeFile(model.getTransactionList());
        //reimbursementModel.updateReimbursementList(model.getTransactionList());
        //reimbursementStorage.writeFile(reimbursementModel.getReimbursementList());
        return commandResult;
    }

    public TransactionList getTransactionListFromFile() throws Exception {
        return this.storage.getTransactionList();
    }

    public void writeIntoTransactionFile() throws Exception {
        //model.writeInTransactionFile();
        storage.writeFile(model.getTransactionList());
    }

    public void setTransaction(Transaction transaction, Transaction newTransaction) throws Exception {
        model.setTransaction(transaction, newTransaction);
    }

    public TransactionList getTransactionList() {
        return model.getTransactionList();
    }
}
