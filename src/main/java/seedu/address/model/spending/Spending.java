package seedu.address.model.spending;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Spending {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    private final Cost cost;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */

    public Spending(Name name, Phone phone, Email email, Cost cost, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, cost, tags);

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.cost = cost;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Cost getCost() {
        return cost;
    }
    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both spending of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two spending.
     */
    public boolean isSameSpending(Spending otherSpending) {
        if (otherSpending == this) {
            return true;
        }

        return otherSpending != null
                && otherSpending.getName().equals(getName())
                && (otherSpending.getPhone().equals(getPhone()) || otherSpending.getEmail().equals(getEmail()));
    }

    /**
     * Returns true if both spending have the same identity and data fields.
     * This defines a stronger notion of equality between two spending.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Spending)) {
            return false;
        }

        Spending otherSpending = (Spending) other;
        return otherSpending.getName().equals(getName())
                && otherSpending.getPhone().equals(getPhone())
                && otherSpending.getCost().equals(getCost())
                && otherSpending.getEmail().equals(getEmail())
                && otherSpending.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, cost, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Cost: ")
                .append(getCost())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
