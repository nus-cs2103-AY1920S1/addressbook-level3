package seedu.address.transaction.logic;

import java.io.IOException;
import java.util.logging.Logger;

import seedu.address.person.commons.core.LogsCenter;
import seedu.address.transaction.logic.commands.Command;
import seedu.address.transaction.logic.commands.CommandResult;
import seedu.address.transaction.logic.parser.TransactionTabParser;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.storage.Storage;
import seedu.address.transaction.util.TransactionList;

/**
 * Manages the logic behind the transaction tab.
 */
public class LogicManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(getClass());
    private final Model model;
    private final Storage storage;
    private final TransactionTabParser parser;
    private final seedu.address.person.model.Model personModel;
    private final seedu.address.reimbursement.model.Model reimbursementModel;
    private final seedu.address.reimbursement.storage.Storage reimbursementStorage;

    public LogicManager(Model transactionModel, Storage transactionStorage,
                        seedu.address.person.model.Model personModel,
                        seedu.address.reimbursement.model.Model reimbursementModel,
                        seedu.address.reimbursement.storage.Storage reimbursementStorage) {
        this.model = transactionModel;
        this.storage = transactionStorage;
        this.parser = new TransactionTabParser();
        this.personModel = personModel;
        this.reimbursementModel = reimbursementModel;
        this.reimbursementStorage = reimbursementStorage;
    }

    @Override
    public CommandResult execute(String commandText)
            throws Exception {
        Command command = parser.parseCommand(commandText, personModel);
        logger.info("Command type: " + command.toString());
        CommandResult commandResult = command.execute(model, personModel);
        model.updateIndexes();
        storage.writeFile(model.getTransactionList());
        reimbursementModel.updateReimbursementList(
                reimbursementStorage.getReimbursementFromFile(model.getTransactionList()));
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
