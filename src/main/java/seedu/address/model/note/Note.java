package seedu.address.model.note;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.note.NoteFragment.NOTE_FRAGMENT_CONTENT_DETECTION_REGEX;
import static seedu.address.model.note.NoteFragment.NOTE_FRAGMENT_END_DETECTION_REGEX;
import static seedu.address.model.note.NoteFragment.NOTE_FRAGMENT_START_DETECTION_REGEX;
import static seedu.address.model.note.NoteFragment.NOTE_FRAGMENT_TAG_DETECTION_REGEX;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.logic.commands.note.NoteFragmentFeatureUtil;
import seedu.address.model.StudyBuddyItem;
import seedu.address.model.tag.Tag;

/**
 * Represents a Note in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Note extends StudyBuddyItem {

    // Identity fields
    private final Title title;
    private final Content content;
    private final List<NoteFragment> noteFragments;

    /**
     * Every field must be present and not null, except for tags.
     */
    public Note(Title title, Content content, Set<Tag> tags) {
        super(tags);
        requireAllNonNull(title, content);
        this.title = title;
        this.content = content;
        this.noteFragments = NoteFragmentFeatureUtil.parseNoteFragmentsFromNote(this);
    }

    public Title getTitle() {
        return title;
    }

    public Content getContent() {
        return content;
    }

    public Content getContentCleanedFromTags() {
        String rawContent = content.toString();
        String cleanedContent = rawContent.replaceAll(NOTE_FRAGMENT_CONTENT_DETECTION_REGEX, "")
                .replaceAll(NOTE_FRAGMENT_TAG_DETECTION_REGEX, " ")
                .replaceAll(NOTE_FRAGMENT_START_DETECTION_REGEX, " ")
                .replaceAll(NOTE_FRAGMENT_END_DETECTION_REGEX, " ")
                .trim();
        return new Content(cleanedContent);
    }

    public List<NoteFragment> getNoteFragments() {
        return noteFragments;
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
