package seedu.address.model.expense;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents an expense in the expense list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Expense {

    // Identity fields
    private final Name name;
    private final Amount amount;

    // Data Fields
    private final Date date;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Expense(Name name, Amount amount, Date date, Set<Tag> tags) {
        requireAllNonNull(name, amount, date, tags);
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Amount getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both expenses of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two expenses.
     */
    public boolean isSameExpense(Expense otherExpense) {
        if (otherExpense == this) {
            return true;
        }

        return otherExpense != null
            && otherExpense.getName().equals(getName())
            && (otherExpense.getAmount().equals(getAmount()) || otherExpense.getDate().equals(getDate()));
    }

    /**
     * Returns true if both expenses have the same identity and data fields.
     * This defines a stronger notion of equality between two expenses.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Expense)) {
            return false;
        }

        Expense otherExpense = (Expense) other;
        return otherExpense.getName().equals(getName())
            && otherExpense.getAmount().equals(getAmount())
            && otherExpense.getDate().equals(getDate())
            && otherExpense.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, amount, date, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
            .append(" Amount: ")
            .append(getAmount())
            .append(" Date: ")
            .append(getDate())
            .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
