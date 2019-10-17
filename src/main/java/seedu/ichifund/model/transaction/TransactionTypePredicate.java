package seedu.ichifund.model.transaction;

import java.util.function.Predicate;

/**
 * Tests that a {@code Transaction}'s {@code TransactionType} matches the given transaction type.
 */
public class TransactionTypePredicate implements Predicate<Transaction> {
    private final TransactionType transactionType;

    public TransactionTypePredicate(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public boolean test(Transaction transaction) {
        return transaction.getTransactionType().equals(transactionType);
    }
}
