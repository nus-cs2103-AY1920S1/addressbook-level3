package seedu.address.model.note;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Date;
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
    private final NoteDescription description;
    private final Content content;
    private final DateModified dateModified;
    private final DateAdded dateAdded;
    private final NumOfAccess numOfAccess;
    // Data fields
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Note(Title title, NoteDescription description, Set<Tag> tags, Content content) {
        requireAllNonNull(title, tags);
        this.title = title;
        this.description = description;
        this.content = content;
        this.dateModified = new DateModified(new Date());
        this.dateAdded = new DateAdded(new Date());
        this.numOfAccess = new NumOfAccess(0);
        this.tags.addAll(tags);
    }

    /**
     * Overloaded constructor with DateModified and NumOfAccess parameters.
     * Every field must be present and not null.
     */
    public Note(Title title, NoteDescription description, Set<Tag> tags, Content content,
                DateModified dateModified, DateAdded dateAdded, NumOfAccess numOfAccess) {
        requireAllNonNull(title, tags);
        this.title = title;
        this.description = description;
        this.content = content;
        this.dateModified = dateModified;
        this.dateAdded = dateAdded;
        this.numOfAccess = numOfAccess;
        this.tags.addAll(tags);
    }

    public NumOfAccess updateNumOfAccess() {
        return numOfAccess.update();
    }

    public NumOfAccess getNumOfAccess() {
        return numOfAccess;
    }

    public DateModified updateDateModified() {
        return dateModified.update();
    }

    public DateAdded getDateAdded() {
        return dateAdded;
    }

    public DateModified getDateModified() {
        return dateModified;
    }

    public Title getTitle() {
        return title;
    }

    public NoteDescription getDescription() {
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
                && otherNote.getTitle().equals(getTitle());
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
                && otherNote.getContent().equals(getContent())
                && otherNote.getNumOfAccess().equals(getNumOfAccess());

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
