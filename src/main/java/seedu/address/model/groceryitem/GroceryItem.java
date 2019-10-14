package seedu.address.model.groceryitem;

import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a grocery item.
 */
public class GroceryItem extends Food {

    private final ExpiryDate expiryDate;

    public GroceryItem(Name name, Amount amount, ExpiryDate expiryDate, Set<Tag> tags) {
        super(name, amount, tags);
        this.expiryDate = expiryDate;
    }

    public ExpiryDate getExpiryDate() {
        return expiryDate;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(this.getName(), this.getAmount(), this.getExpiryDate(), this.getTags());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" ")
                .append(getAmount())
                .append(" ")
                .append(getExpiryDate())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
