package seedu.address.stubs;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.transaction.model.transaction.Transaction;

/**
 * Represents a home tab's model stub.
 */
public class TransactionModelStubWithTransaction extends TransactionModelStub {
    private Transaction transaction;
    private Predicate<Transaction> predicate;


    public TransactionModelStubWithTransaction(Transaction transaction) {
        requireNonNull(transaction);
        this.transaction = transaction;
    }

    @Override
    public boolean hasTransaction(Transaction person) {
        requireNonNull(person);
        return this.transaction.equals(transaction);
    }

    @Override
    public void resetPredicate() {
        this.predicate = transaction -> true;
    }
}
