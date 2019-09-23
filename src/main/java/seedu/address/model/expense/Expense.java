package seedu.address.model.expense;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Expense in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Expense {

    // identity fields
    private final UniqueIdentifier uniqueIdentifier;
    // data fields
    private final Description description;
    private final Price price;

    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Expense(Description description, Price price, Set<Tag> tags, UniqueIdentifier uniqueIdentifier) {
        requireAllNonNull(description, price, tags, uniqueIdentifier);
        this.description = description;
        this.price = price;
        this.uniqueIdentifier = uniqueIdentifier;
        this.tags.addAll(tags);
    }

    public Description getDescription() {
        return description;
    }

    public Price getPrice() {
        return price;
    }

    public UniqueIdentifier getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both expenses of the same unique identifier.
     * This defines a weaker notion of equality between two expenses.
     */
    public boolean isSameExpense(Expense otherExpense) {
        if (otherExpense == this) {
            return true;
        }

        return otherExpense != null
                && otherExpense.getUniqueIdentifier().equals(getUniqueIdentifier());
    }

    /**
     * Returns true if both expenses have the same unique identifier and data fields.
     * This defines a stronger notion of equality between two expenses.
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
        return otherExpense.getUniqueIdentifier().equals(getUniqueIdentifier())
                && otherExpense.getDescription().equals(getDescription())
                && otherExpense.getPrice().equals(getPrice())
                && otherExpense.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, price, tags, uniqueIdentifier);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Expense: ")
                .append(getDescription())
                .append(" Price: ")
                .append(getPrice())
                .append(" [Tags: ");
        getTags().forEach(builder::append);
        builder.append("]");
        return builder.toString();
    }

}
