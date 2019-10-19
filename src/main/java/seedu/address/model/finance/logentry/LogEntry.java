package seedu.address.model.finance.logentry;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents an entry in the finance log.
 * Guarantees: details are present and not null, field values are validated, mutable.
 */
public class LogEntry {

    // Identity fields
    private final Amount amount;
    private final TransactionDate transactionDate;
    private final Description description;


    /**
     * Every field must be present and not null.
     */
    public LogEntry(Amount amount, TransactionDate transactionDate, Description description) {
        requireAllNonNull(amount, transactionDate, description);
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.description = description;
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


    /**
     * Returns true if both log entries have the same identity fields.
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
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LogEntry)) {
            return false;
        }

        LogEntry otherLogEntry = (LogEntry) other;
        return otherLogEntry.getAmount().equals(getAmount())
                && otherLogEntry.getTransactionDate().equals(getTransactionDate())
                && otherLogEntry.getDescription().equals(getDescription());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(amount, transactionDate, description);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getAmount())
                .append(" Transaction Date: ")
                .append(getTransactionDate())
                .append(" Description: ")
                .append(getDescription());
        return builder.toString();
    }

}
