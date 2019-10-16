package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents an Income.
 */
public class Income extends Entry {

    private static final String ENTRY_TYPE = "Income";
    private final Time time;

    public Income(Description desc, Time time, Amount amt, Set<Tag> tags) {
        super(desc, amt, tags);
        this.time = time;
    }

    public String getType() {
        return this.ENTRY_TYPE;
    }

    public Time getTime() {
        return this.time;
    }

    /**
     * Returns true if both incomes have the same data fields.
     * This defines a stronger notion of equality between two entries.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Income)) {
            return false;
        } else if (!(other instanceof Wish)) {
            return false;
        }

        Income otherIncome = (Income) other;
        return otherIncome.getDesc().equals(getDesc())
                && otherIncome.getAmount().equals(getAmount())
                && otherIncome.getTags().equals(getTags())
                && otherIncome.getTime().equals(getTime());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(ENTRY_TYPE + ": ")
                .append(getDesc())
                .append(" Amount: ")
                .append(getAmount())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        builder.append("(" + time + ")");
        return builder.toString();
    }

}
