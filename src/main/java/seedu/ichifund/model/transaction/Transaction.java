package seedu.ichifund.model.transaction;

import static seedu.ichifund.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.ichifund.model.Description;
import seedu.ichifund.model.amount.Amount;
import seedu.ichifund.model.date.Date;
import seedu.ichifund.model.date.Day;
import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;
import seedu.ichifund.model.repeater.RepeaterUniqueId;

/**
 * Represents a Transaction in IchiFund.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Transaction implements Comparable<Transaction> {
    private final Description description;
    private final Amount amount;
    private final Category category;
    private final Date date;
    private final TransactionType transactionType;
    private final RepeaterUniqueId repeaterUniqueId;

    /**
     * Every field must be present and not null.
     */
    public Transaction(Description description, Amount amount, Category category, Date date,
                       TransactionType transactionType, RepeaterUniqueId repeaterUniqueId) {
        requireAllNonNull(description, amount, category);
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.date = date;
        this.transactionType = transactionType;
        this.repeaterUniqueId = repeaterUniqueId;
    }

    public Description getDescription() {
        return description;
    }

    public Amount getAmount() {
        return amount;
    }

    public Category getCategory() {
        return category;
    }

    public Date getDate() {
        return date;
    }

    public Day getDay() {
        return date.getDay();
    }

    public Month getMonth() {
        return date.getMonth();
    }

    public Year getYear() {
        return date.getYear();
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public RepeaterUniqueId getRepeaterUniqueId() {
        return repeaterUniqueId;
    }

    public boolean isExpenditure() {
        return transactionType.isExpenditure();
    }

    public boolean isIn(Month month) {
        return getDate().isIn(month);
    }

    public boolean isIn(Year year) {
        return getDate().isIn(year);
    }

    public boolean isIn(Category category) {
        return getCategory().equals(category);
    }

    public boolean isFromRepeater() {
        return !repeaterUniqueId.isEmpty();
    }

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
                && otherTransaction.getTransactionType().equals(getTransactionType())
                && otherTransaction.getRepeaterUniqueId().equals(getRepeaterUniqueId());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, amount, category, date, transactionType, repeaterUniqueId);
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
                .append(getDate())
                .append(" Transaction Type: ")
                .append(getTransactionType());
        return builder.toString();
    }

    @Override
    public int compareTo(Transaction other) {
        int dateComparison = getDate().compareTo(other.getDate());
        int categoryComparison = getCategory().compareTo(other.getCategory());
        if (dateComparison != 0) {
            return dateComparison;
        } else if (categoryComparison != 0) {
            return categoryComparison;
        } else {
            return getDescription().compareTo(other.getDescription());
        }
    }
}
