package seedu.address.model.note;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Note in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Note {

    // Identity fields
    private final Title title;
    private final Content content;

    // Data field
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null, except for tags.
     */
    public Note(Title title, Content content, Set<Tag> tags) {
        requireAllNonNull(title, content);
        this.title = title;
        this.content = content;
        this.tags.addAll(tags);
    }

    public Title getTitle() {
        return title;
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

    public boolean containsTag(Tag tag) {
        return this.tags.contains(tag);
    }

    /**
     * Returns true if both notes of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two notes.
     */
    public boolean isSameNote(Note otherNote) {
        if (otherNote == this) {
            return true;
        }

        return otherNote != null && otherNote.getTitle().equals(getTitle());
    }

    /**
     * Returns true if both notes have the same identity and data fields.
     * This defines a stronger notion of equality between two notes.
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
                && otherNote.getContent().equals(getContent())
                && otherNote.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, content, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("\nTitle: ")
                .append(getTitle())
                .append("\nContent: ")
                .append(getContent())
                .append("\nTags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
