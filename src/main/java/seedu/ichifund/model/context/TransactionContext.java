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
import seedu.ichifund.model.transaction.TransactionType;
import seedu.ichifund.model.transaction.TransactionTypePredicate;

/**
 * Unmodifiable context for filtering a list of transactions.
 */
public class TransactionContext implements Context<Transaction> {

    private final Month month;
    private final Year year;
    private final Optional<Category> category;
    private final Optional<TransactionType> transactionType;

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
        this.transactionType = Optional.empty();
    }

    private TransactionContext(Month month, Year year, Optional<Category> category,
                               Optional<TransactionType> transactionType) {
        this.month = month;
        this.year = year;
        this.category = category;
        this.transactionType = transactionType;
    }

    public Month getMonth() {
        return month;
    }

    public Year getYear() {
        return year;
    }

    public TransactionContext withMonth(Month month) {
        return new TransactionContext(month, this.year, this.category, this.transactionType);
    }

    public TransactionContext withYear(Year year) {
        return new TransactionContext(this.month, year, this.category, this.transactionType);
    }

    public TransactionContext withCategory(Optional<Category> category) {
        if (category.isEmpty()) {
            return this;
        } else if (category.get() == Category.CATEGORY_ALL) {
            return new TransactionContext(this.month, this.year, Optional.empty(), this.transactionType);
        } else {
            return new TransactionContext(this.month, this.year, category, this.transactionType);
        }
    }

    public TransactionContext withType(Optional<TransactionType> transactionType) {
        if (transactionType.isEmpty()) {
            return this;
        } else if (transactionType.get() == TransactionType.TRANSACTION_TYPE_ALL) {
            return new TransactionContext(this.month, this.year, this.category, Optional.empty());
        } else {
            return new TransactionContext(this.month, this.year, this.category, transactionType);
        }
    }

    @Override
    public Predicate<Transaction> getPredicate() {
        Predicate<Transaction> predicate = new TransactionDatePredicate(month, year);
        if (category.isPresent() && category.get() != Category.CATEGORY_ALL) {
            predicate = predicate
                    .and(new TransactionCategoryPredicate(category.get()));
        }

        if (transactionType.isPresent() && transactionType.get() != TransactionType.TRANSACTION_TYPE_ALL) {
            predicate = predicate
                    .and(new TransactionTypePredicate(transactionType.get()));
        }

        return predicate;
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
