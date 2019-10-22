package seedu.address.model.note;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import javafx.scene.image.Image;

/**
 * Represents an NUStudy lecture note. Its title and content are guaranteed non-null;
 * its associated picture <i>may</i> be null.
 */
public class Note {
    private final Title title;
    private final Content content;
    private final Image image;

    /**
     * Constructs a new lecture note. Both fields must be present and non-null. The image field is set to null.
     *
     * @param title The lecture note's title, which must be unique among all lecture notes.
     * @param content The lecture note's content (newlines are supported), which do not have to be unique.
     */
    public Note(Title title, Content content) {
        requireAllNonNull(title, content);
        this.title = title;
        this.content = content;
        this.image = null;
    }

    /**
     * Constructs a new lecture note with an image. The image may be null, in which case NUStudy proceeds as with
     * the two-argument constructor.
     *
     * @param image The image associated with this lecture note.
     * @see Note#Note(Title, Content)
     */
    public Note(Title title, Content content, Image image) {
        requireAllNonNull(title, content);
        this.title = title;
        this.content = content;
        this.image = image;
    }

    public Title getTitle() {
        return title;
    }

    public Content getContent() {
        return content;
    }

    public Image getImage() {
        return image;
    }

    public String getImageUrl() {
        return image != null ? image.getUrl() : "none";
    }

    /**
     * Returns true if both lecture notes have the same title.
     * This defines a weaker notion of equality.
     */
    public boolean isSameNote(Note other) {
        if (other == this) {
            return true;
        }

        return other != null && other.getTitle().equals(getTitle());
    }

    /**
     * Returns true if both lecture notes have the same title, content and image.
     * This defines a stronger notion of equality.
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
                && otherNote.getImageUrl().equals(getImageUrl());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, content, getImageUrl());
    }

    @Override
    public String toString() {
        return getTitle() + "\n" + getContent() + "\n[" + getImageUrl() + "]";
    }
}
