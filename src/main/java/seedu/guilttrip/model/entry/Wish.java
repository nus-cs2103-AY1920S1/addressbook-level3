package seedu.guilttrip.model.entry;

import java.util.Set;

import seedu.guilttrip.model.tag.Tag;

/**
 * Represents an Wish.
 */
public class Wish extends Entry {
    private static final String ENTRY_TYPE = "Wish";

    public Wish(Category cat, Description desc, Date date, Amount amount, Set<Tag> tags) {
        super(cat, desc, date, amount, tags);
    }

    public String getType() {
        return ENTRY_TYPE;
    }

    /**
     * Returns a new Wish if and only if it's category is edited.
     */
    public Wish modifiedWish(String newName) {
        Category newCategory = new Category(newName, super.getCategory().getCategoryType());
        return new Wish(newCategory, super.getDesc(), super.getDate(), this.getAmount(), super.getTags());
    }

    /**
     * Returns true if both wishes have the same data fields.
     * This defines a stronger notion of equality between two wishes.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Wish)) {
            return false;
        }

        Wish otherWish = (Wish) other;
        return otherWish.getCategory().equals(getCategory())
                && otherWish.getDesc().equals(getDesc())
                && otherWish.getAmount().equals(getAmount())
                && otherWish.getTags().equals(getTags())
                && otherWish.getDate().equals(getDate());
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
