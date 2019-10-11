package thrift.model.transaction;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import thrift.model.tag.Tag;

/**
 * Represents an Expense in the transactions list.
 * Guarantees: details are present and not null, field values are validated and immutable.
 */
public class Expense extends Transaction {

    //Data fields
    private final Description description;
    private final TransactionDate date;
    private final Value value;
    private final Remark remark;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Expense(Description description, Value value, Remark remark, TransactionDate date, Set<Tag> tags) {
        this.description = description;
        this.value = value;
        this.remark = remark;
        this.date = date;
        this.tags.addAll(tags);
    }

    public Description getDescription() {
        return description;
    }

    public TransactionDate getDate() {
        return date;
    }

    public Value getValue() {
        return value;
    }

    public Remark getRemark() {
        return remark;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both expenses have the same description and value. This allows easier
     * cloning of expense transactions in the future.
     */
    public boolean isSameTransaction(Transaction otherExpense) {
        if (otherExpense == this) {
            return true;
        }

        return otherExpense != null
                && otherExpense.getDescription().equals(getDescription())
                && otherExpense.getValue().equals(getValue());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Expense)) {
            return false;
        }

        Expense otherExpense = (Expense) other;
        return otherExpense.getDescription().equals(getDescription())
                && otherExpense.getDate().equals(getDate())
                && otherExpense.getValue().equals(getValue())
                && otherExpense.getRemark().equals(getRemark())
                && otherExpense.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own.
        return Objects.hash(description, date, value, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("[-] ")
                .append(getDescription())
                .append(" ($")
                .append(getValue())
                .append(") Date: ")
                .append(getDate())
                .append(" Remarks: ")
                .append(getRemark())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
