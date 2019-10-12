package seedu.address.model.note;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Note in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Note {

    // Identity fields
    private final Title title;
    private final Content content;

    public Note() {
        this.title = new Title("sample title");
        this.content = new Content("sample content");
    }
    
    /**
     * Every field must be present and not null.
     */
    public Note(Title title, Content content) {
        requireAllNonNull(title, content);
        this.title = title;
        this.content = content;
    }

    public Title getTitle() {
        return title;
    }

    public Content getContent() {
        return content;
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
                && otherNote.getContent().equals(getContent());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, content);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Title: ")
                .append(getTitle())
                .append("\nContent: ")
                .append(getContent());
        return builder.toString();
    }

}
