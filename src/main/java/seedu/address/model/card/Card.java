package seedu.address.model.card;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Card.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Card {

    // field values
    private final Name name;
    private final Description description;
    private final Set<Tag> tags = new HashSet<>();

    // stateful objects
    private HintSupplier hintSupplier;

    /**
     * Every field must be present and not null.
     */
    public Card(Name name, Description description, Set<Tag> tags) {
        requireAllNonNull(name, description, tags);
        this.name = name;
        this.description = description;
        this.tags.addAll(tags);
        this.hintSupplier = new HintSupplier(name.value);
    }

    public Name getName() {
        return name;
    }

    public Description getDescription() {
        return description;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both cards have the same name.
     */
    public boolean isSameName(Card other) {
        if (other == null) {
            return false;
        }
        return getName().equals(other.getName());
    }

    /**
     * Returns the next hint of the name, and null if no more hints available.
     */
    public Hint getHint() {
        return hintSupplier.get();
    }

    /**
     * Returns true if both cards have the same name, description, and tags.
     * Defines a stronger equality than {@link Card#isSameName(Card)}.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Card)) {
            return false;
        }

        Card otherPerson = (Card) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getDescription().equals(getDescription())
                && otherPerson.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, description, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Description: ")
                .append(getDescription())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
