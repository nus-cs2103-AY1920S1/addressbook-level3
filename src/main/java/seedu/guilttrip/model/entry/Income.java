package seedu.guilttrip.model.entry;

import java.util.Set;

import seedu.guilttrip.model.tag.Tag;

/**
 * Represents an Income.
 */
public class Income extends Entry {
    private static final String ENTRY_TYPE = "Income";

    public Income(Category cat, Description desc, Date time, Amount amt, Set<Tag> tags) {
        super(cat, desc, time, amt, tags);
    }

    public String getType() {
        return ENTRY_TYPE;
    }

    /**
     * Returns a new Income if and only if it's category is edited.
     */
    public Income modifiedCategory(String newName) {
        Category newCategory = new Category(newName, super.getCategory().categoryType);
        return new Income(newCategory, super.getDesc(), super.getDate(), super.getAmount(), super.getTags());
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
        }

        Income otherIncome = (Income) other;
        return otherIncome.getCategory().equals(getCategory())
                && otherIncome.getDesc().equals(getDesc())
                && otherIncome.getAmount().equals(getAmount())
                && otherIncome.getTags().equals(getTags())
                && otherIncome.getDate().equals(getDate());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Category: ")
                .append(getCategory())
                .append(" | Description: ")
                .append(getDesc())
                .append(" | Amount: ")
                .append(getAmount())
                .append(" | Tags: ");
        getTags().forEach(builder::append);
        builder.append(" (" + getDate() + ")");
        return builder.toString();
    }

}
