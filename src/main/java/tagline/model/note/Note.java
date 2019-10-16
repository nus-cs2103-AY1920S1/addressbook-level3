package tagline.model.note;

import static tagline.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import tagline.model.tag.Tag;

/**
 * Represents a Note in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Note {

    // Identity fields
    private final NoteId noteid;
    private final Title title;
    private final Content content;
    private final TimeCreated timeCreated;

    // Data fields
    private final TimeLastEdited timeLastEdited;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Note(NoteId noteid, Title title, Content content, TimeCreated timeCreated,
                TimeLastEdited timeLastEdited, Set<Tag> tags) {

        requireAllNonNull(noteid, title, content, timeCreated, timeLastEdited, tags);

        this.noteid = noteid;
        this.title = title;
        this.content = content;
        this.timeCreated = timeCreated;
        this.timeLastEdited = timeLastEdited;
        this.tags.addAll(tags);
    }

    public NoteId getNoteId() {
        return noteid;
    }

    public Title getTitle() {
        return title;
    }

    public Content getContent() {
        return content;
    }

    public TimeCreated getTimeCreated() {
        return timeCreated;
    }

    public TimeLastEdited getTimeLastEdited() {
        return timeLastEdited;
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
             //this part should be .equals(getId()), title will be optional i guess
             && otherNote.getNoteId().equals(getNoteId());
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
        return otherNote.getNoteId().equals(getNoteId())
                && otherNote.getTitle().equals(getTitle())
                && otherNote.getContent().equals(getContent())
                && otherNote.getTimeCreated().equals(getTimeCreated())
                && otherNote.getTimeLastEdited().equals(getTimeLastEdited())
                && otherNote.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(noteid, title, content, timeCreated, timeLastEdited, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getNoteId())
                .append(" Title: ")
                .append(getTitle())
                .append(" Content: ")
                .append(getContent())
                .append(" Time Created: ")
                .append(getTimeCreated())
                .append(" Time Last Edited: ")
                .append(getTimeLastEdited())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
