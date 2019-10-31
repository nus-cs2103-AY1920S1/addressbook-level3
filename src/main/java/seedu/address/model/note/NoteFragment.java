package seedu.address.model.note;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Set;

import seedu.address.model.StudyBuddyItem;
import seedu.address.model.tag.Tag;

/**
 * Represents a NoteFragment in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class NoteFragment extends StudyBuddyItem {
    public static final String NOTE_FRAGMENT_CONTENT_DETECTION_REGEX = "C/";
    public static final String NOTE_FRAGMENT_END_DETECTION_REGEX = "(\\s*)+\\*/(\\s*)+";
    public static final String NOTE_FRAGMENT_START_DETECTION_REGEX = "(\\s*)+/\\*(\\s*)+";
    public static final String NOTE_FRAGMENT_TAG_DETECTION_REGEX = "TAG/\\S+ ";

    /**
     * Indicates the Title of the Note that this NoteFragment was copied from.
     */
    private final Title title;

    /**
     * Indicates the Content of this NoteFragment.
     */
    private final Content content;


    /**
     * Every field must be present and not null, except for tags.
     */
    public NoteFragment(Title title, Content content, Set<Tag> tags) {
        super(tags);
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
    public boolean isSameNote(NoteFragment otherNote) {
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

        if (!(other instanceof NoteFragment)) {
            return false;
        }

        NoteFragment otherNote = (NoteFragment) other;
        return otherNote.getTitle().equals(getTitle())
                && otherNote.getContent().equals(getContent())
                && otherNote.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, content, getTags());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("\n\tTitle: ")
                .append(getTitle())
                .append("\n\tContent: ")
                .append(getContent())
                .append("\n\tTags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
