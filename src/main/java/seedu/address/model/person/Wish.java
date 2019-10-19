package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents an Expense.
 */
public class Wish extends Entry {

    private static final String ENTRY_TYPE = "Wish";

    public Wish(Description desc, Time time, Amount amount, Set<Tag> tags) {
        super(desc, time, amount, tags);
    }

    public String getType() {
        return this.ENTRY_TYPE;
    }


    /**
     * Returns true if both expenses have the same data fields.
     * This defines a stronger notion of equality between two entries.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Expense)) {
            return false;
        } else if (!(other instanceof Income)) {
            return false;
        }

        Wish otherWish = (Wish) other;
        return otherWish.getDesc().equals(getDesc())
                && otherWish.getAmount().equals(getAmount())
                && otherWish.getTags().equals(getTags())
                && otherWish.getTime().equals(getTime());
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
        builder.append("(" + getTime() + ")");
        return builder.toString();
    }

}
