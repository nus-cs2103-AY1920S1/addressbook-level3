package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents an Entry in the finance app.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Entry {

    // Identity fields
    private final Description desc;
    private final Amount amt;
    private final Date date;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */

    public Entry(Description desc, Date date, Amount amount, Set<Tag> tags) {
        requireAllNonNull(desc, date, amount, tags);
        this.desc = desc;
        this.amt = amount;
        this.date = date;
        this.tags.addAll(tags);
    }

    public Description getDesc() {
        return desc;
    }

    public Amount getAmount() {
        return amt;
    }

    public String getType() {
        return "entry";
    }

    public Date getDate() {
        return this.date;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both entries of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two entries.
     */

    public boolean isSameEntry(Entry otherEntry) {
        if (otherEntry == this) {
            return true;
        }

        return otherEntry != null
                && otherEntry.getDesc().equals(getDesc())
                && otherEntry.getAmount().equals(getAmount())
                && this.equals(otherEntry);
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

        if (!(equalClass(other))) {
            return false;
        } else {
            if (this instanceof Expense) {
                return ((Expense) this).equals((Expense) other);
            } else if (this instanceof Income) {
                return ((Income) this).equals((Income) other);
            } else if (this instanceof Wish) {
                return ((Wish) this).equals((Wish) other);
            } else if (this instanceof Budget) {
                return ((Budget) this).equals((Budget) other);
            } else {
                return false;
            }
        }
    }

    /**
     * Checks if children class of this instance is same as that of other.
     * @param other object to compare to.
     * @return boolean.
     */
    protected boolean equalClass(Object other) {
        if (this instanceof Expense && !(other instanceof Expense)) {
            return false;
        } else if (this instanceof Income && !(other instanceof Income)) {
            return false;
        } else if (this instanceof Wish && !(other instanceof Wish)) {
            return false;
        } else {
            return !(this instanceof Budget) || other instanceof Budget;
        }
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(desc, amt, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDesc())
                .append(" Amount: ")
                .append(amt)
               .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }


}
