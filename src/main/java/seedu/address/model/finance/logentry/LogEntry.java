package seedu.address.model.finance.logentry;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.finance.attributes.Category;
import seedu.address.model.finance.attributes.TransactionMethod;

/**
 * Represents an entry in the finance log.
 * Guarantees: details are present and not null, field values are validated, mutable.
 */
public abstract class LogEntry {

    // Basic fields associated with an log entry
    protected final Amount amount;
    protected final TransactionDate transactionDate;
    protected final Description description;

    // Additional optional fields
    private final TransactionMethod tMethod;
    private final Set<Category> categories = new HashSet<>();


    /**
     * Every field must be present and not null.
     */
    public LogEntry(Amount amount, TransactionDate transactionDate, Description description,
                    TransactionMethod transactionMethod, Set<Category> categories) {
        requireAllNonNull(amount, transactionDate, description,
                 transactionMethod, categories);
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.description = description;
        this.tMethod = transactionMethod;
        this.categories.addAll(categories);
    }

    public Amount getAmount() {
        return amount;
    }

    public TransactionDate getTransactionDate() {
        return transactionDate;
    }

    public Description getDescription() {
        return description;
    }

    public TransactionMethod getTransactionMethod() {
        return tMethod;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Category> getCategories() {
        return Collections.unmodifiableSet(categories);
    }

    public abstract String getLogEntryType();

    /**
     * Returns true if both log entries have the same basic fields.
     */
    public boolean isSameLogEntry(LogEntry otherLogEntry) {
        if (otherLogEntry == this) {
            return true;
        }

        return otherLogEntry != null
                && otherLogEntry.getAmount().equals(getAmount())
                && (otherLogEntry.getTransactionDate().equals(getTransactionDate())
                && otherLogEntry.getDescription().equals(getDescription()));
    }

    /**
     * Returns true if both persons have the same basic fields,
     * additional field,
     * and of the same child class of LogEntry.
     * This defines a stronger notion of equality between two log entries.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(this.getClass() == other.getClass())) {
            return false;
        }

        LogEntry otherLogEntry = (LogEntry) other;
        return otherLogEntry.getAmount().equals(getAmount())
                && otherLogEntry.getTransactionDate().equals(getTransactionDate())
                && otherLogEntry.getDescription().equals(getDescription())
                && otherLogEntry.getTransactionMethod().equals(getTransactionMethod())
                && otherLogEntry.getCategories().equals(getCategories());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(amount, transactionDate, description, categories);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Amount: ")
                .append(getAmount())
                .append(" Transaction Date: ")
                .append(getTransactionDate())
                .append(" Description: ")
                .append(getDescription());
        return builder.toString();
    }

}
