package seedu.moneygowhere.model.spending;

import static seedu.moneygowhere.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.moneygowhere.model.tag.Tag;

/**
 * Represents a Spending in the MoneyGoWhere list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Spending {

    // Identity fields
    private final Name name;
    private final Date date;
    private final Remark remark;

    private final Cost cost;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Spending(Name name, Date date, Remark remark, Cost cost, Set<Tag> tags) {
        requireAllNonNull(name, date, remark, cost, tags);

        this.name = name;
        this.date = date;
        this.remark = remark;
        this.cost = cost;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public LocalDate getDateValue() {
        return date.dateValue;
    }

    public Remark getRemark() {
        return remark;
    }

    public Cost getCost() {
        return cost;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both spending of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two spending.
     */
    public boolean isSameSpending(Spending otherSpending) {
        if (otherSpending == this) {
            return true;
        }

        return otherSpending != null
                && otherSpending.getName().equals(getName())
                && otherSpending.getCost().equals(getCost())
                && otherSpending.getDate().equals(getDate());
    }

    /**
     * Returns true if both spending have the same identity and data fields.
     * This defines a stronger notion of equality between two spending.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Spending)) {
            return false;
        }

        Spending otherSpending = (Spending) other;
        return otherSpending.getName().equals(getName())
                && otherSpending.getDate().equals(getDate())
                && otherSpending.getCost().equals(getCost())
                && otherSpending.getRemark().equals(getRemark())
                && otherSpending.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, date, remark, cost, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Date: ")
                .append(getDate());

        if (!(getRemark().toString().equals(""))) {
            builder.append(" Remark: ")
                    .append(getRemark());
        }
        builder.append(" Cost: ")
                .append(getCost())
                .append(" SGD");
        if (getTags().size() > 0) {
            builder.append(" Tags: ");
            getTags().forEach(builder::append);
        }
        return builder.toString();
    }
}
