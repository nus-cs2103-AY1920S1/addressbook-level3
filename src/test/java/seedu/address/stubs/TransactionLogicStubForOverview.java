package seedu.address.stubs;

import java.io.IOException;

import seedu.address.transaction.logic.Logic;
import seedu.address.transaction.model.TransactionList;
import seedu.address.transaction.model.transaction.Transaction;
import seedu.address.util.CommandResult;

/**
 * Stub of TransactionLogic for OverviewLogic tests.
 */
public class TransactionLogicStubForOverview implements Logic {

    private TransactionList transactionList;

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

    @Override
    public void appendToTransactionFile(Transaction transaction) throws Exception {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public void updateTransactionStorage() throws IOException {
        throw new AssertionError("This method should not be called");
    }
}
