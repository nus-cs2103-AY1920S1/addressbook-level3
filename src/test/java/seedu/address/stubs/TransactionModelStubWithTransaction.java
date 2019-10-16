package seedu.address.stubs;

import static java.util.Objects.requireNonNull;

import seedu.address.transaction.model.Transaction;

public class TransactionModelStubWithTransaction extends TransactionModelStub {
    private Transaction transaction;

    public TransactionModelStubWithTransaction(Transaction transaction) {
        requireNonNull(transaction);
        this.transaction = transaction;
    }

    @Override
    public boolean hasTransaction(Transaction person) {
        requireNonNull(person);
        return this.transaction.equals(transaction);
    }
}
