package seedu.ichifund.model.transaction;

import java.util.function.Predicate;

/**
 * Tests that a {@code Transaction}'s {@code Category} matches the given category.
 */
public class TransactionCategoryPredicate implements Predicate<Transaction> {

    private final Category category;

    public TransactionCategoryPredicate(Category category) {
        this.category = category;
    }

    @Override
    public boolean test(Transaction transaction) {
        return transaction.isIn(category);
    }
}
