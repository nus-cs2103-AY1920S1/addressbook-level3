package seedu.ichifund.model.context;

import static java.util.Objects.requireNonNull;
import static seedu.ichifund.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;
import seedu.ichifund.model.transaction.Category;
import seedu.ichifund.model.transaction.Transaction;
import seedu.ichifund.model.transaction.TransactionType;

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
        requireNonNull(transaction);
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

    public TransactionContext(Month month, Year year, Optional<Category> category,
                               Optional<TransactionType> transactionType) {
        requireAllNonNull(month, year, category, transactionType);
        this.month = month;
        this.year = year;
        this.category = category;
        this.transactionType = transactionType;
    }

    public boolean hasCategory() {
        return category.isPresent();
    }

    public boolean hasTransactionType() {
        return transactionType.isPresent();
    }

    public Category getCategory() {
        return category.orElse(Category.CATEGORY_DEFAULT);
    }

    public TransactionType getTransactionType() {
        return transactionType.orElse(TransactionType.TRANSACTION_TYPE_DEFAULT);
    }

    public Month getMonth() {
        return month;
    }

    public Year getYear() {
        return year;
    }

    private boolean categoryMatches(Transaction transaction) {
        return category.isEmpty()
                || category.get().equals(transaction.getCategory());
    }

    private boolean transactionTypeMatches(Transaction transaction) {
        return transactionType.isEmpty()
                || transactionType.get().equals(transaction.getTransactionType());
    }

    @Override
    public Predicate<Transaction> getPredicate() {
        Predicate<Transaction> predicate = transaction -> transaction.isIn(month) && transaction.isIn(year);
        if (category.isPresent() && category.get() != Category.CATEGORY_ALL) {
            predicate = predicate
                    .and(transaction -> transaction.getCategory().equals(category.get()));
        }

        if (transactionType.isPresent() && transactionType.get() != TransactionType.TRANSACTION_TYPE_ALL) {
            predicate = predicate
                    .and(transaction -> transaction.getTransactionType().equals(transactionType.get()));
        }

        return predicate;
    }

    @Override
    public TransactionContext getAccommodatingContext(Transaction transaction) {
        Optional<Category> category = this.category;
        Optional<TransactionType> transactionType = this.transactionType;

        if (!categoryMatches(transaction)) {
            category = Optional.empty();
        }

        if (!transactionTypeMatches(transaction)) {
            transactionType = Optional.empty();
        }

        return new TransactionContext(
                transaction.getDate().getMonth(),
                transaction.getDate().getYear(),
                category,
                transactionType);
    }

    @Override
    public String toString() {
        String string = getMonth().wordString().toUpperCase() + " " + getYear().toString();
        if (transactionType.isPresent()) {
            string += " " + transactionType.get().toExtendedString().toUpperCase();
        }

        if (category.isPresent()) {
            string += " - " + category.get().toString().toUpperCase();
        }

        return string;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TransactionContext)) {
            return false;
        }

        TransactionContext otherContext = (TransactionContext) other;

        return otherContext.month.equals(month)
                && otherContext.year.equals(year)
                && otherContext.category.equals(category)
                && otherContext.transactionType.equals(transactionType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(month, year, category);
    }

    /**
     * Builder class to construct a TransactionContext object.
     */
    public static class TransactionContextBuilder {
        private Month month;
        private Year year;
        private Optional<Category> category;
        private Optional<TransactionType> transactionType;

        public TransactionContextBuilder(TransactionContext transactionContext) {
            this.month = transactionContext.month;
            this.year = transactionContext.year;
            this.category = transactionContext.category;
            this.transactionType = transactionContext.transactionType;
        }

        /**
         * Returns a new {@code TransitionContextBuilder} updated with the input month.
         * Returns the same object if there is no month.
         */
        public TransactionContextBuilder withMonth(Optional<Month> month) {
            if (!month.isEmpty()) {
                this.month = month.get();
            }

            return this;
        }

        /**
         * Returns a new {@code TransitionContextBuilder} updated with the input year.
         * Returns the same object if there is no year.
         */
        public TransactionContextBuilder withYear(Optional<Year> year) {
            if (!year.isEmpty()) {
                this.year = year.get();
            }

            return this;
        }

        /**
         * Returns a new {@code TransitionContextBuilder} updated with the input category.
         * Returns the same object if there is no category.
         */
        public TransactionContextBuilder withCategory(Optional<Category> category) {
            if (category.isEmpty()) {
                // Modify nothing
            } else if (category.get() == Category.CATEGORY_ALL) {
                this.category = Optional.empty();
            } else {
                this.category = category;
            }

            return this;
        }

        /**
         * Returns a new {@code TransitionContextBuilder} updated with the input transaction type.
         * Returns the same object if there is no transaction type.
         */
        public TransactionContextBuilder withType(Optional<TransactionType> transactionType) {
            if (transactionType.isEmpty()) {
                // Modify nothing
            } else if (transactionType.get() == TransactionType.TRANSACTION_TYPE_ALL) {
                this.transactionType = Optional.empty();
            } else {
                this.transactionType = transactionType;
            }

            return this;
        }

        /**
         * Returns the TransactionContext built by the builder.
         */
        public TransactionContext build() {
            requireAllNonNull(month, year, category, transactionType);
            return new TransactionContext(month, year, category, transactionType);
        }
    }
}
