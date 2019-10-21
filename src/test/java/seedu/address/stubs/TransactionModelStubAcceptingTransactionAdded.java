package seedu.address.stubs;

import java.util.ArrayList;
import java.util.function.Predicate;

import seedu.address.transaction.model.Transaction;

/**
 * Represents a home tab's model stub.
 */
public class TransactionModelStubAcceptingTransactionAdded extends TransactionModelStub {
    final ArrayList<Transaction> transactionsAdded;
    private Predicate<Transaction> predicate;

    public TransactionModelStubAcceptingTransactionAdded() {
        transactionsAdded = new ArrayList<>();
    }

    public TransactionModelStubAcceptingTransactionAdded(ArrayList<Transaction> arr) {
        this.transactionsAdded = arr;
    }

    @Override
    public void addTransaction(Transaction transaction) {
        transactionsAdded.add(transaction);
    }

    public ArrayList<Transaction> getTransactionsAdded() {
        return transactionsAdded;
    }

    @Override
    public void deleteTransaction(int index) {
        transactionsAdded.remove(index - 1);
    }

    @Override
    public void resetPredicate() {
        this.predicate = transaction -> true;
    }

}
