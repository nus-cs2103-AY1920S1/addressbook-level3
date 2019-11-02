package seedu.address.model.note;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import javafx.scene.image.Image;
import seedu.address.commons.util.AppUtil;

/**
 * Represents an NUStudy lecture note. Its title and content are guaranteed non-null;
 * its associated picture <i>may</i> be null.
 */
public class Note {
    private final Title title;
    private final Content content;
    private boolean needsImage;
    private Image image = null;

    /**
     * Constructs a new lecture note. The title and content must be present and non-null;
     * if needsImage is true, allows the selection of an image later with finalizeImage().
     *
     * @param title The lecture note's title, which must be unique among all lecture notes.
     * @param content The lecture note's content (newlines are supported), which do not have to be unique.
     * @param needsImage Whether an image is intended for this lecture note.
     */
    public Note(Title title, Content content, boolean needsImage) {
        requireAllNonNull(title, content);
        this.title = title;
        this.content = content;
        this.needsImage = needsImage;
    }

    /**
     * Constructs a new lecture note, but without an image.
     *
     * @see Note#Note(Title, Content, boolean)
     */
    public Note(Title title, Content content) {
        this(title, content, false);
    }

    /**
     * Constructs a new lecture note with an image already present, which may be null.
     * This does not allow further editing of the image through finalizeImage().
     *
     * @param image The image associated with this lecture note.
     * @see Note#Note(Title, Content)
     */
    public Note(Title title, Content content, Image image) {
        this(title, content);
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
     * Finalizes this note's image if one is needed, copying it into the parent directory provided.
     * This method's main body can only be executed once.
     */
    public void finalizeImage(Path parent) {
        requireAllNonNull(parent);
        if (!needsImage) {
            return;
        }
        image = AppUtil.selectImage();
        if (image != null) {
            // selectImage() prefixes the URL with "file:", so we only need to remove the prefix
            Path sourcePath = Paths.get(image.getUrl().substring(5));
            Path destPath = parent.resolve(sourcePath.getFileName().toString());
            try {
                Files.copy(sourcePath, destPath, StandardCopyOption.REPLACE_EXISTING);
                image = new Image("file:" + destPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        needsImage = false;
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
