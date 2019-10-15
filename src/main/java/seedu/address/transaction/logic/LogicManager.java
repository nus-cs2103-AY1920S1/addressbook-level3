package seedu.address.transaction.logic;

import java.io.IOException;

import seedu.address.transaction.commands.Command;
import seedu.address.transaction.commands.CommandResult;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.storage.StorageManager;
import seedu.address.transaction.util.TransactionList;

/**
 * Manages the logic behind the transaction tab.
 */
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
        this.parser = new TransactionTabParser();
        this.personStorage = personStorage;
        this.personModel = personModel;
        this.reimbursementModel = reimbursementModel;
        this.reimbursementStorage = reimbursementStorage;
    }

    @Override
    public CommandResult execute(String commandText)
            throws Exception {
        model.resetPredicate();
        Command command = parser.parseCommand(commandText,
                model.getTransactionList().size(), personModel);
        CommandResult commandResult = command.execute(model, personModel);
        model.updateIndexes();
        personStorage.saveAddressBook(personModel.getAddressBook());
        storage.writeFile(model.getTransactionList());
        reimbursementModel.updateReimbursementList(model.getTransactionList());
        reimbursementStorage.writeFile(reimbursementModel.getReimbursementList());
        return commandResult;
    }

    @Override
    public void writeIntoTransactionFile() throws IOException {
        storage.writeFile(model.getTransactionList());
    }

    @Override
    public void setTransaction(Transaction transaction, Transaction newTransaction) {
        model.setTransaction(transaction, newTransaction);
    }

    @Override
    public TransactionList getTransactionList() {
        return model.getTransactionList();
    }

    @Override
    public TransactionList getFilteredList() {
        return model.getFilteredList();
    }

    @Override
    public void addTransaction(Transaction transaction) {
        model.addTransaction(transaction);
    }
}
