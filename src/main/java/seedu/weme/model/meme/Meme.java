package seedu.weme.model.meme;

import static seedu.weme.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.weme.model.imagePath.ImagePath;
import seedu.weme.model.tag.Tag;

/**
 * Represents a Meme in Weme.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Meme {

    // Identity fields
    private final ImagePath imagePath;

    // Data fields
    private final Description description;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Meme(ImagePath imagePath, Description description, Set<Tag> tags) {
        requireAllNonNull(imagePath, description, tags);
        this.imagePath = imagePath;
        this.description = description;
        this.tags.addAll(tags);
    }

    /**
     * Overloaded Constructor used to generate imported memes.
     */
    public Meme(ImagePath path) {
        this.imagePath = path;
        this.description = new Description("");
    }

    public Description getDescription() {
        return description;
    }

    public ImagePath getImagePath() {
        return imagePath;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both memes of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two memes.
     */
    public boolean isSameMeme(Meme otherMeme) {
        if (otherMeme == this) {
            return true;
        }

        return otherMeme != null
                && otherMeme.getImagePath().equals(getImagePath());
    }

    /**
     * Returns true if both memes have the same identity and data fields.
     * This defines a stronger notion of equality between two memes.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Meme)) {
            return false;
        }

        Meme otherMeme = (Meme) other;
        return otherMeme.getImagePath().equals(getImagePath())
                && otherMeme.getDescription().equals(getDescription())
                && otherMeme.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(imagePath, description, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Description: ")
                .append(getDescription())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
