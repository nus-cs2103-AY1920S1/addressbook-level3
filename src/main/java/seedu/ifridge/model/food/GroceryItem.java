package seedu.ifridge.model.food;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.ifridge.model.tag.Tag;

/**
 * Represents a grocery item.
 */
public class GroceryItem extends Food {

    private final ExpiryDate expiryDate;
    private final Set<Tag> tags = new HashSet<>();

    public GroceryItem(Name name, Amount amount, ExpiryDate expiryDate, Set<Tag> tags) {
        super(name, amount);
        this.expiryDate = expiryDate;
        this.tags.addAll(tags);
    }

    public ExpiryDate getExpiryDate() {
        return expiryDate;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public boolean isEmpty() {
        return Amount.getValue(getAmount()) == 0;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(this.getName(), this.getAmount(), this.getExpiryDate(), this.getTags());
    }

    @Override
    public boolean isSameFood(Food anotherFood) {
        if (!(anotherFood instanceof GroceryItem)) {
            return false;
        } else {
            return this.getName().equals(anotherFood.getName())
                    && this.getExpiryDate().equals(((GroceryItem) anotherFood).getExpiryDate());
        }
    }

    /**
     * Checks if an item has expired
     */
    public boolean hasExpired() {
        Date itemExpiry = expiryDate.getValue();
        Calendar cal = Calendar.getInstance();
        Date current = cal.getTime();

        int remDays = (int) ((itemExpiry.getTime() - current.getTime()) / (24 * 60 * 60 * 1000));

        // Items expiring on the day itself are considered expired
        if (remDays >= 0) {
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof GroceryItem)) {
            return false;
        } else {
            return ((GroceryItem) o).getName().equals(this.getName())
                    && ((GroceryItem) o).getExpiryDate().equals(this.getExpiryDate())
                    && ((GroceryItem) o).getAmount().equals(this.getAmount())
                    && ((GroceryItem) o).getTags().equals(this.getTags());
        }
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Name:")
                .append(getName())
                .append(" Amount:")
                .append(getAmount())
                .append(" ExpiryDate:")
                .append(getExpiryDate())
                .append(" Tags:");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
