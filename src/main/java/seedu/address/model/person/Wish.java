package seedu.address.model.person;

import java.time.LocalDate;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents an Expense.
 */
public class Wish extends Entry {

    private static final String ENTRY_TYPE = "Wish";
    private final LocalDate time;

    public Wish(Description desc, Time time, Amount amount, Set<Tag> tags) {
        super(desc, amount, tags);
        String[] date = time.toString().split(" ");
        this.time = LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]),
                Integer.parseInt(date[2]));
    }

    public Wish(Description desc, LocalDate time, Amount amount, Set<Tag> tags) {
        super(desc, amount, tags);
        this.time = time;
    }

    public String getType() {
        return this.ENTRY_TYPE;
    }

    public LocalDate getTime() {
        return this.time;
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
        builder.append("(" + time + ")");
        return builder.toString();
    }

}
