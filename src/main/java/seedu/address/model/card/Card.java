package seedu.address.model.card;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.UidGenerator;
import seedu.address.model.tag.Tag;

/**
 * Represents a Card.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Card {
    private final String id;
    private final Word word;
    private final Meaning meaning;
    private final Set<Tag> tags = new HashSet<>();

    // stateful objects, will create a new hintSupplier every game session. (by instantiaing a new card).
    private HintSupplier hintSupplier;

    /**
     * Every field must be present and not null.
     */
    public Card(String id, Word word, Meaning meaning, Set<Tag> tags) {
        requireAllNonNull(id, word, meaning, tags);
        this.id = id;
        this.word = word;
        this.meaning = meaning;
        this.tags.addAll(tags);
        this.hintSupplier = new HintSupplier(word.getValue());
    }

    /**
     * Returns a deep copy of the current {@code Card}.
     * This is to prevent hintSupplier carrying state to another game session due to referring to same
     * card object.
     */
    @Override
    public Card clone() {
        return new Card(this.id, this.word, this.meaning, this.tags);
    }

    /**
     * Creates a new {@code card} with a new id.
     */
    public static Card createNewCard(Word word, Meaning meaning, Set<Tag> tags) {
        return new Card(UidGenerator.get(), word, meaning, tags);
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
     * Returns the uniquely identifiable ID of this card
     */
    public String getId() {
        return id;
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
    public FormattedHint getHint() {
        return hintSupplier.get();
    }

    public int getHintFormatSize() {
        return word.toString().length();
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
                .append("\nMeaning: ")
                .append(getMeaning())
                .append("\nTags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
