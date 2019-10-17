package seedu.ichifund.model.transaction;

import java.util.function.Predicate;

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
