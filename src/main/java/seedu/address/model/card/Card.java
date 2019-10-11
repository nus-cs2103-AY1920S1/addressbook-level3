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
    private final Word word;
    private final Meaning meaning;
    private final Set<Tag> tags = new HashSet<>();

    // stateful objects
    private HintSupplier hintSupplier;

    /**
     * Every field must be present and not null.
     */
    public Card(Word word, Meaning meaning, Set<Tag> tags) {
        requireAllNonNull(word, meaning, tags);
        this.word = word;
        this.meaning = meaning;
        this.tags.addAll(tags);
        this.hintSupplier = new HintSupplier(word.value);
    }

    public Word getWord() {
        return word;
    }

    public Meaning getMeaning() {
        return meaning;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both cards have the same meaning.
     */
    public boolean isSameMeaning(Card other) {
        if (other == null) {
            return false;
        }
        return getMeaning().equals(other.getMeaning());
    }

    /**
     * Returns the next hint of the name, and null if no more hints available.
     */
    public Hint getHint() {
        return hintSupplier.get();
    }

    /**
     * Returns true if both cards have the same name, description, and tags.
     * Defines a stronger equality than {@link Card#isSameMeaning(Card)}.
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
        return otherPerson.getWord().equals(getWord())
                && otherPerson.getMeaning().equals(getMeaning())
                && otherPerson.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(word, meaning, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getWord())
                .append(" Meaning: ")
                .append(getMeaning())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
