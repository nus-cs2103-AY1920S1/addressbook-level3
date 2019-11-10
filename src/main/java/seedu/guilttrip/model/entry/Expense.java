package seedu.guilttrip.model.entry;

import java.util.Set;

import seedu.guilttrip.model.tag.Tag;

/**
 * Represents an Expense.
 */
public class Expense extends Entry {
    private static final String ENTRYTYPE = "Expense";

    public Expense(Category cat, Description desc, Date time, Amount amount, Set<Tag> tags) {
        super(cat, desc, time, amount, tags);
    }

    public String getType() {
        return this.ENTRYTYPE;
    }

    /**
     * Returns a new Expense if and only if it's category is edited.
     */
    public Expense modifiedCategory(String newName) {
        Category newCategory = new Category(newName, super.getCategory().categoryType);
        return new Expense(newCategory, super.getDesc(), super.getDate(), super.getAmount(), super.getTags());
    }

    /**
     * Returns true if both expenses have the same data fields. This defines a
     * stronger notion of equality between two entries.
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

        return otherExpense.getCategory().equals(getCategory()) && otherExpense.getDesc().equals(getDesc())
                && otherExpense.getAmount().equals(getAmount()) && otherExpense.getTags().equals(getTags())
                && otherExpense.getDate().equals(getDate());
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
        builder.append(" (" + this.getDate() + ") ");
        return builder.toString();
    }

}
