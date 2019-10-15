package seedu.address.model.eatery;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Eatery in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Eatery {

    // Identity fields
    private final Name name;
    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Eatery(Name name, Address address, Set<Tag> tags) {
        requireAllNonNull(name, address, tags);
        this.name = name;
        this.address = address;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }


    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both eateries of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two eateries.
     */
    public boolean isSameEatery(Eatery otherEatery) {
        if (otherEatery == this) {
            return true;
        }

        return otherEatery != null
                && otherEatery.getName().equals(getName());
    }

    /**
     * Returns true if both eateries have the same identity and data fields.
     * This defines a stronger notion of equality between two eateries.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Eatery)) {
            return false;
        }

        Eatery otherEatery = (Eatery) other;
        return otherEatery.getName().equals(getName())
                && otherEatery.getAddress().equals(getAddress())
                && otherEatery.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, address, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Address: ")
                .append(getAddress())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
