package seedu.ichifund.model.context;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;
import seedu.ichifund.model.transaction.Category;
import seedu.ichifund.model.transaction.Transaction;
import seedu.ichifund.model.transaction.TransactionCategoryPredicate;
import seedu.ichifund.model.transaction.TransactionDatePredicate;

/**
 * Unmodifiable context for filtering a list of transactions.
 */
public class TransactionContext implements Context<Transaction> {

    private final Month month;
    private final Year year;
    private final Optional<Category> category;

    /**
     * Public constructor for initialization of context.
     * @param transaction
     */
    public TransactionContext(Optional<Transaction> transaction) {
        if (transaction.isEmpty()) {
            this.month = Month.getCurrent();
            this.year = Year.getCurrent();
        } else {
            this.month = transaction.get().getDate().getMonth();
            this.year = transaction.get().getDate().getYear();
        }

        this.category = Optional.empty();
    }

    private TransactionContext(Month month, Year year) {
        this.month = month;
        this.year = year;
        this.category = Optional.empty();
    }

    private TransactionContext(Month month, Year year, Optional<Category> category) {
        this.month = month;
        this.year = year;
        this.category = category;
    }

    public Month getMonth() {
        return month;
    }

    public Year getYear() {
        return year;
    }

    public TransactionContext getUpdatedContext(Month month) {
        return new TransactionContext(month, this.year, this.category);
    }

    public TransactionContext getUpdatedContext(Year year) {
        return new TransactionContext(this.month, year, this.category);
    }

    public TransactionContext getUpdatedContext(Optional<Category> category) {
        return new TransactionContext(this.month, this.year, category);
    }

    public TransactionContext getContextWithAllCategories() {
        return new TransactionContext(this.month, this.year);
    }

    @Override
    public Predicate<Transaction> getPredicate() {
        Predicate<Transaction> datePredicate = new TransactionDatePredicate(month, year);
        if (category.isPresent()) {
            return datePredicate.and(new TransactionCategoryPredicate(category.get()));
        } else {
            return datePredicate;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TransactionContext // instanceof handles nulls
                && month.equals((((TransactionContext) other).month))
                && year.equals((((TransactionContext) other).year))
                && category.equals((((TransactionContext) other).category))); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(month, year, category);
    }
}
