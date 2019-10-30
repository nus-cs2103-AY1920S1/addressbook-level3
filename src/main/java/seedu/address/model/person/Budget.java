package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Budget.
 */
public class Budget extends Entry {

    private static final String ENTRY_TYPE = "Budget";

    private Amount spent;

    public Budget(Category cat, Description desc, Date date, Amount amount, Set<Tag> tags) {
        super(cat, desc, date, amount, tags);
        spent = new Amount(0);
    }

    /**
     * Returns amount spent out of the budget.
     *
     * TODO: display on UI side panel
     *
     * @return amount Amount spent (calculated from expenses)
     */
    public Amount getSpent() {
        return spent;
    }

    public String getType() {
        return this.ENTRY_TYPE;
    }



    /**
     * Returns true if both budgets have the same data fields.
     * This defines a stronger notion of equality between two entries.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Budget)) {
            return false;
        }

        Budget otherBudget = (Budget) other;
        return otherBudget.getCategory().equals(getCategory())
                && otherBudget.getDesc().equals(getDesc())
                && otherBudget.getAmount().equals(getAmount())
                && otherBudget.getTags().equals(getTags())
                && otherBudget.getDate().equals(getDate());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(ENTRY_TYPE + ": ")
                .append(" | Category: ")
                .append(getCategory())
                .append(" Description: ")
                .append(getDesc())
                .append(" Amount: ")
                .append(getAmount())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        builder.append("(" + getDate() + ")");
        return builder.toString();
    }
}
