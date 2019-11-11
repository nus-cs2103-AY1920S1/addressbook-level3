package seedu.address.transaction.logic;

import java.io.IOException;
import java.util.logging.Logger;

import seedu.address.person.commons.core.LogsCenter;
import seedu.address.person.model.CheckAndGetPersonByNameModel;
import seedu.address.transaction.logic.commands.Command;
import seedu.address.transaction.logic.parser.TransactionTabParser;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.TransactionList;
import seedu.address.transaction.model.transaction.Transaction;
import seedu.address.transaction.storage.Storage;
import seedu.address.util.CommandResult;

/**
 * Manages the logic behind the transaction tab.
 */
public class LogicManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(getClass());
    private final Model model;
    private final Storage storage;
    private final TransactionTabParser parser;
    private final CheckAndGetPersonByNameModel personModel;

    public LogicManager(Model transactionModel, Storage transactionStorage,
                        CheckAndGetPersonByNameModel personModel) {
        this.model = transactionModel;
        this.storage = transactionStorage;
        this.parser = new TransactionTabParser();
        this.personModel = personModel;
    }

    @Override
    public CommandResult execute(String commandText)
            throws Exception {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText, personModel);
        logger.info("Command type: " + command.toString());
        CommandResult commandResult = command.execute(model, personModel);
        model.updateIndexes();
        storage.writeFile(model.getTransactionList());
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

    @Override
    public void appendToTransactionFile(Transaction transaction) throws Exception {
        storage.appendToTransaction(transaction);
    }

    @Override
    public void updateTransactionStorage() throws IOException {
        storage.writeFile(model.getTransactionList());
    }
}
