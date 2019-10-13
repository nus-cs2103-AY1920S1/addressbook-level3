package seedu.ichifund.model.transaction;

import static seedu.ichifund.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.ichifund.model.Amount;
import seedu.ichifund.model.Description;
import seedu.ichifund.model.date.Date;

/**
 * Represents a Transaction in IchiFund.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Transaction {
    private final Amount amount;
    private final Description description;
    private final Category category;
    private final Date date;
    private final boolean isExpenditure;

    /**
     * Every field must be present and not null.
     */
    public Transaction(Description description, Amount amount, Category category, Date date, TransactionType transactionType) {
        requireAllNonNull(description, amount, category);
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.date = date;
        this.isExpenditure = transactionType.isExpenditure();
    }

    public Amount getAmount() {
        return amount;
    }

    public Description getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public Date getDate() {
        return date;
    }

    public boolean isExpenditure() {
        return isExpenditure;
    }


    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Transaction)) {
            return false;
        }

        Transaction otherTransaction = (Transaction) other;
        return otherTransaction.getDescription().equals(getDescription())
                && otherTransaction.getAmount().equals(getAmount())
                && otherTransaction.getCategory().equals(getCategory())
                && otherTransaction.getDate().equals(getDate())
                && (otherTransaction.isExpenditure() == isExpenditure());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(amount, description, category, date, isExpenditure);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription())
                .append(" Amount: ")
                .append(getAmount())
                .append(" Category: ")
                .append(getCategory())
                .append(" Date: ")
                .append(getDate());
        return builder.toString();
    }

}
