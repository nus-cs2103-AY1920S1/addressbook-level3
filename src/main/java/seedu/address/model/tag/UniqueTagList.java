package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.tag.exceptions.DuplicateTagException;
import seedu.address.model.tag.exceptions.InvalidTagModificationException;
import seedu.address.model.tag.exceptions.TagNotFoundException;


/**
 * A list of tags that enforces uniqueness between its elements and does not allow nulls.
 * A tag is considered unique by comparing using {@code Tag#isSameTag(Tag)}.
 * As such, adding and updating of tags uses Tag#isSameTag(Tag) for equality so as
 * to ensure that the Tag being added or updated is unique in terms of identity in the UniqueTagList.
 * However, the removal of a Tag uses Tag#equals (Object) so as to ensure that the tag with
 * exactly the same fields will be removed.
 * Supports a minimal set of list operations.
 *
 * @see Tag#isSameTag(Tag)
 */
public class UniqueTagList implements Iterable<Tag>, Cloneable {

    private final ObservableList<Tag> internalList = FXCollections.observableArrayList();
    private final ObservableList<Tag> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);
    private final HashMap<String, Tag> mapTags = new HashMap<String, Tag>();

    private boolean containsPriorityTag = false;
    private PriorityTag priorityTag = null;

    /**
     * Constructs a {@code UniqueTagList}.
     */
    public UniqueTagList() {
    }

    /**
     * Returns true if the list contains an equivalent Tag as the given argument.
     */
    public boolean contains(Tag toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameTag);
    }

    /**
     * Checks if the list contains a tag with the given tag name.
     */
    public boolean containsTagWithName(String tagName) {
        requireNonNull(tagName);
        return mapTags.containsKey(tagName.toUpperCase());
    }

    /**
     * Adds a {@code Tag} to the list.
     * The tag must not already exist in the list.
     *
     * @param toAdd The {@code Tag} to be added.
     * @throws DuplicateTagException if the list already contains the tag.
     */
    public void addTag(Tag toAdd) throws DuplicateTagException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTagException();
        }
        if (toAdd.isPriority()) {
            containsPriorityTag = true;
            priorityTag = (PriorityTag) toAdd;
        }
        internalList.add(toAdd);
        mapTags.put(toAdd.getTagName().toUpperCase(), toAdd);
    }

    /**
     * Replaces the UserTag {@code target} in the list with {@code editedTag}.
     * {@code target} must exist in the list.
     * The UserTag identity of {@code editedTag} must not be the same as another existing
     * UserTag in the list.
     */
    public void setUserTag(UserTag target, UserTag editedTag) throws TagNotFoundException, DuplicateTagException {
        requireAllNonNull(target, editedTag);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TagNotFoundException();
        }

        if (!target.isSameTag(editedTag) && contains(editedTag)) {
            throw new DuplicateTagException();
        }

        internalList.set(index, editedTag);
        mapTags.remove(target.getTagName());
        mapTags.put(editedTag.getTagName().toUpperCase(), editedTag);
    }

    public DefaultTag getDefaultTag(String defaultTagName) throws TagNotFoundException {
        Tag correspondingTag = mapTags.get(defaultTagName.toUpperCase());
        if (correspondingTag == null || !correspondingTag.isDefault()) {
            throw new TagNotFoundException();
        }
        return (DefaultTag) correspondingTag;
    }

    /**
     * Gets a specific tag that has the given tag name.
     *
     * @param tagName Name of the tag
     * @return Tag
     */
    public Tag getTag(String tagName) throws TagNotFoundException {
        if (!mapTags.containsKey(tagName.toUpperCase())) {
            throw new TagNotFoundException();
        }
        return mapTags.get(tagName.toUpperCase());
    }

    /**
     * Removes completed tags. Should only be used when updating current semesters.
     */
    public void removeCompletedTag(DefaultTag toRemove) {
        if (!toRemove.getDefaultTagType().equals(DefaultTagType.COMPLETED)) {
            throw new InvalidTagModificationException();
        }
        if (!internalList.remove(toRemove)) {
            throw new TagNotFoundException();
        }
        mapTags.remove(toRemove.getTagName());
    }

    /**
     * Removes the equivalent UserTag from the list.
     * The UserTag must exist in the list.
     */
    public void removeTag(Tag toRemove) throws TagNotFoundException, InvalidTagModificationException {
        requireNonNull(toRemove);
        if (toRemove.isDefault()) {
            throw new InvalidTagModificationException();
        }
        if (!internalList.remove(toRemove)) {
            throw new TagNotFoundException();
        }
        if (toRemove.isPriority()) {
            containsPriorityTag = false;
            priorityTag = null;
        }
        mapTags.remove(toRemove.getTagName());
    }

    public void setTags(UniqueTagList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
        mapTags.clear();
        HashMap<String, Tag> newMap = replacement.getMapTags();
        Set<String> newKeys = newMap.keySet();
        for (String newKey : newKeys) {
            mapTags.put(newKey.toUpperCase(), newMap.get(newKey));
        }
    }

    /**
     * Replaces the contents of this list with {@code Tags}.
     * {@code Tags} must not contain duplicate Tags.
     */
    public void setTags(List<Tag> tags) throws DuplicateTagException {
        requireAllNonNull(tags);

        if (!userTagsAreUnique(tags)) {
            throw new DuplicateTagException();
        }

        internalList.setAll(tags);
        mapTags.clear();
        for (Tag newTag : tags) {
            mapTags.put(newTag.getTagName().toUpperCase(), newTag);
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Tag> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    public List<String> asListOfStrings() {
        return asUnmodifiableObservableList().stream().map(Tag::getTagName).collect(Collectors.toList());
    }

    @Override
    public Iterator<Tag> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTagList // instanceof handles nulls
                && internalList.equals(((UniqueTagList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public Object clone() {
        UniqueTagList clone = new UniqueTagList();
        for (Tag tag : this) {
            clone.addTag(tag);
        }
        return clone;
    }

    private HashMap<String, Tag> getMapTags() {
        return mapTags;
    }

    /**
     * Returns true if {@code Tags} contains only unique UserTags.
     */
    private boolean userTagsAreUnique(List<Tag> tags) {
        for (int i = 0; i < tags.size() - 1; i++) {
            if (tags.get(i).isDefault()) {
                continue;
            }
            for (int j = i + 1; j < tags.size(); j++) {
                if (tags.get(i).isDefault()) {
                    continue;
                }
                if (tags.get(i).isSameTag(tags.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Initialises default tags for the unique tag list, using all values in {@code DefaultTagType}.
     */
    public void initDefaultTags() {
        for (DefaultTagType defaultTagType : DefaultTagType.values()) {
            addTag(new DefaultTag(defaultTagType));
        }
    }

    /**
     * Updates the hashmap of tags when tags are renamed.
     */
    public void updateTagMaps(String originalTagName, String newTagName) {
        Tag tag = mapTags.get(originalTagName.toUpperCase());
        mapTags.remove(originalTagName.toUpperCase());
        mapTags.put(newTagName.toUpperCase(), tag);
    }

    public boolean containsPriorityTag() {
        return containsPriorityTag;
    }

    public PriorityTag getPriorityTag() {
        return priorityTag;
    }

}
