package tagline.model.tag;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

import javafx.collections.ObservableList;

import tagline.model.contact.ContactId;

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
     * Returns number of tags in the {@code TagBook}.
     */
    public int getNumberOfTags() {
        return tags.size();
    }

    /**
     * Returns true if a tag with the same identity as {@code tag} exists in the tag book.
     */
    public boolean hasTag(Tag tag) {
        requireNonNull(tag);
        return tags.containsTag(tag);
    }

    /**
     * Returns true if a tag with the same content as {@code tag} exists in the tag book.
     */
    public boolean hasTag(TagType tagType, String content) {
        requireNonNull(tagType);
        requireNonNull(content);
        return tags.containsTag(tagType, content);
    }

    /**
     * Returns an id of an existing tag with the same content as {@code tag} in the tag book.
     */
    public Optional<TagId> findTagId(TagType tagType, String content) {
        requireNonNull(tagType);
        requireNonNull(content);
        return tags.findTagId(tagType, content);
    }

    /**
     * Adds a tag to the address book.
     * The tag must not already exist in the address book.
     */
    public void addTag(Tag p) {
        tags.addTag(p);
    }

    /**
     * Adds a tag to the address book.
     *
     * @param tagId     Id of the tag which will be added.
     * @param tagType   Type of the tag which will be added.
     * @param content   Content of the tag which will be added.
     */
    public void addTag(TagId tagId, TagType tagType, String content) {
        switch (tagType) {
        case CONTACT_TAG:
            tags.addTag(new ContactTag(tagId, new ContactId(content)));
            break;
        default:
            break;
        }
    }

    /**
     * Finds all tags with ID equal to {@code tagId}.
     * Returns an unmodifiable list.
     */
    public List<Tag> findTag(TagId tagId) {
        return tags.findTag(tagId);
    }

    /**
     * Find the maximum value of {@code TagId} in the tag book.
     */
    public OptionalLong findMaxValueOfTagId() {
        return tags.findMaxValueOfTagId();
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
