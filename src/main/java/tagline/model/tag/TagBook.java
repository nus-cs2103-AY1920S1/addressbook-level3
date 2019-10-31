package tagline.model.tag;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;

/**
 * Wraps all tag data at a level suitable for other components.
 * Duplicates are not allowed.
 */
public class TagBook implements ReadOnlyTagBook {

    private final UniqueTagList tags;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        tags = new UniqueTagList();
    }

    public TagBook() {
    }

    /**
     * Creates an TagBook using the Tags in the {@code toBeCopied}
     */
    public TagBook(ReadOnlyTagBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the tag list with {@code tags}.
     * {@code tags} must not contain duplicate tags.
     */
    public void setTags(List<Tag> tags) {
        this.tags.setTags(tags);
    }

    /**
     * Resets the existing data of this {@code TagBook} with {@code newData}.
     */
    public void resetData(ReadOnlyTagBook newData) {
        requireNonNull(newData);
        setTags(newData.getTagList());
    }

    //// tag-level operations

    /**
     * Returns true if a tag with the same identity as {@code tag} exists in the tag book.
     */
    public boolean hasTag(Tag tag) {
        requireNonNull(tag);
        return tags.containsTag(tag);
    }

    /**
     * Adds a tag to the tag book.
     * The tag must not already exist in the tag book.
     */
    public void addTag(Tag p) {
        tags.addTag(p);
    }

    /**
     * Removes a tag from the tag book.
     * The tag must already exist in the tag book.
     */

    public void removeTag(Tag p) {
        tags.removeTag(p);
    }

    /**
     * Finds a tag in {@code TagBook} which are equal to {@code tag}.
     */
    public Optional<Tag> findTag(Tag tag) {
        return tags.findTag(tag);
    }

    //// util methods

    @Override
    public String toString() {
        return tags.asUnmodifiableObservableList().size() + " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof TagBook // instanceof handles nulls
            && tags.equals(((TagBook) other).tags));
    }

    @Override
    public int hashCode() {
        return tags.hashCode();
    }
}
