package seedu.address.stubs;

import seedu.address.transaction.logic.Logic;
import seedu.address.transaction.logic.commands.CommandResult;
import seedu.address.transaction.model.TransactionList;
import seedu.address.transaction.model.transaction.Transaction;

import java.io.IOException;

public class TransactionLogicStubForOverview implements Logic {

    TransactionList transactionList;

    public TransactionLogicStubForOverview(TransactionList transactionList) {
        this.transactionList = transactionList;
    }

    public CommandResult execute(String commandText) throws Exception {
        throw new AssertionError("This method shoould not be called.");
    }

    public void writeIntoTransactionFile() throws IOException {
        throw new AssertionError("This method should not be called.");
    }

    public void setTransaction(Transaction transaction, Transaction newTransaction) {
        throw new AssertionError("This method should not be called");
    }

    public TransactionList getTransactionList() {
        return transactionList;
    }

    public TransactionList getFilteredList() {
        throw new AssertionError("This method should not be called");
    }

    public void addTransaction(Transaction transaction) {
        throw new AssertionError("This method should not be called");
    }
}
