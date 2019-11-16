package seedu.ifridge.model.food;

import static seedu.ifridge.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Food {

    // Identity fields
    private final Name name;
    private final Amount amount;

    /**
     * Every field must be present and not null.
     */
    public Food(Name name, Amount amount) {
        requireAllNonNull(name);
        this.name = name;
        this.amount = amount;
        //this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Amount getAmount() {
        return amount;
    }

    ///**
    // Returns an immutable tag set, which throws {@code UnsupportedOperationException}
    // * if modification is attempted.
    // */
    //public Set<Tag> getTags() {
    //    return Collections.unmodifiableSet(tags);
    //}

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameFood(Food otherFood) {
        if (otherFood == this) {
            return true;
        }

        return otherFood != null
                && otherFood.getName().equals(getName());
    }

    /**
     * Checks whether the item has the same name as the other.
     * @param other food item to be checked against
     * @return
     */
    public boolean isSameName(Food other) {
        String thisName = this.getName().toString().toUpperCase();
        String otherName = other.getName().toString().toUpperCase();

        if (!thisName.equals(otherName)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, amount);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        //        .append(" Tags: ");
        //getTags().forEach(builder::append);
        return builder.toString();
    }
}
