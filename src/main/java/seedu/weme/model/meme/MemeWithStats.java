package seedu.weme.model.meme;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import seedu.weme.model.tag.Tag;

import static seedu.weme.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Meme in the meme book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class MemeWithStats extends Meme {

    private int likes;

    /**
     * Every field must be present and not null.
     */
    public MemeWithStats(ImagePath filePath, Description description, Set<Tag> tags, int likes) {
        super(filePath, description, tags);
        this.likes = likes;
    }

    public int getLikes() {
        return likes;
    }

    /**
     * Returns true if both memes have the same identity and data fields.
     * This defines a stronger notion of equality between two memes.
     */
    @Override
    public boolean equals(Object other) {
        MemeWithStats otherMeme = (MemeWithStats) other;
        return otherMeme.equals(this)
                && (otherMeme.getLikes() == getLikes());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(getFilePath(), getDescription(), getTags(), likes);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString())
                .append("Likes: ")
                .append(likes);
        return builder.toString();
    }

}
