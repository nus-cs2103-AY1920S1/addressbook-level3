package seedu.address.model.note;

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
public class Note {

    // Identity fields
    private final Title title;
    private final Description description;
    private final Content content;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Note(Title title, Description description, Set<Tag> tags, Content content) {
        requireAllNonNull(title, tags);
        this.title = title;
        this.description = description;
        this.content = content;
        this.tags.addAll(tags);
    }

    public Title getTitle() {
        return title;
    }

    public Description getDescription() {
        return description;
    }

    public Content getContent() {
        return content;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameNote(Note otherNote) {
        if (otherNote == this) {
            return true;
        }

        return otherNote != null
                && otherNote.getTitle().equals(getTitle())
                && otherNote.getDescription().equals(getDescription())
                && otherNote.getContent().equals(getContent());


    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Note)) {
            return false;
        }

        Note otherNote = (Note) other;
        return otherNote.getTitle().equals(getTitle())
                && otherNote.getTags().equals(getTags())
                && otherNote.getDescription().equals(getDescription())
                && otherNote.getContent().equals(getContent());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, description, tags, content);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle() + " ")
                .append(getDescription() + " ")
                .append(getContent() + " ")
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
