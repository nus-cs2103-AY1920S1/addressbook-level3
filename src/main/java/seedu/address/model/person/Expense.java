package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents an Expense.
 */
public class Expense extends Entry {

    private static final String ENTRY_TYPE = "Expense";
    private final Time time;

    public Expense(Description desc, Time time, Amount amount, Set<Tag> tags) {
        super(desc, amount, tags);
        this.time = time;
    }

    public String getType() {
        return this.ENTRY_TYPE;
    }

    public Time getTime() {
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
        }

        Expense otherExpense = (Expense) other;
        return otherExpense.getDesc().equals(getDesc())
                && otherExpense.getAmount().equals(getAmount())
                && otherExpense.getTags().equals(getTags())
                && otherExpense.getTime().equals(getTime());
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
