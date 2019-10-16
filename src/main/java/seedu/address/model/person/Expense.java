package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents an Expense.
 */
public class Expense extends Entry {

    private static final String ENTRYTYPE = "Expense";

    public Expense(Description desc, Time time, Amount amount, Set<Tag> tags) {
        super(desc,time,amount,tags);
    }

    public String getType() {
        return this.ENTRYTYPE;
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
        } else if (!(other instanceof Wish)) {
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
        builder.append(ENTRYTYPE + ": ")
                .append(getDesc())
                .append(" Amount: ")
                .append(getAmount())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        builder.append("(" + this.getTime() + ")");
        return builder.toString();
    }

}
