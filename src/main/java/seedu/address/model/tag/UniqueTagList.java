package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.tag.exceptions.DuplicateTagException;
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
     * Adds a {@code Tag} to the list.
     * The tag must not already exist in the list.
     * @param toAdd The {@code Tag} to be added.
     * @throws DuplicateTagException if the list already contains the tag.
     */
    public void addTag(Tag toAdd) throws DuplicateTagException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTagException();
        }
        internalList.add(toAdd);
        mapTags.put(toAdd.getTagName(), toAdd);
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
        mapTags.put(editedTag.getTagName(), editedTag);
    }

    public DefaultTag getDefaultTag(String defaultTagName) throws TagNotFoundException {
        Tag correspondingTag = mapTags.get(defaultTagName);
        if (!correspondingTag.isDefault()) {
            throw new TagNotFoundException();
        }
        return (DefaultTag) correspondingTag;
    }

    /**
     * Removes the equivalent UserTag from the list.
     * The UserTag must exist in the list.
     */
    public void remove(UserTag toRemove) throws TagNotFoundException {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new TagNotFoundException();
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
            mapTags.put(newKey, newMap.get(newKey));
        }
    }

    //    /**
    //     * Replaces the contents of this list with {@code UserTags}.
    //     * {@code UserTags} must not contain duplicate UserTags.
    //     */
    //    public void setTags(List<UserTag> userTags) throws DuplicateTagException {
    //        requireAllNonNull(userTags);
    //        if (!userTagsAreUnique(userTags)) {
    //            throw new DuplicateTagException();
    //        }
    //        internalList.setAll(userTags);
    //        mapTags.clear();
    //        initDefaultTags();
    //        for (UserTag newUserTag: userTags) {
    //            mapTags.put(newUserTag.getTagName(), newUserTag);
    //        }
    //    }

    /**
     * Replaces the contents of this list with {@code Tags}.
     * {@code Tags} must not contain duplicate Tags.
     */
    public void setTags(List<Tag> tags) throws DuplicateTagException {
        requireAllNonNull(tags);

        List<UserTag> userTags = new ArrayList<>();
        for (Tag tag : tags) {
            if (tag instanceof UserTag) {
                userTags.add((UserTag) tag);
            }
        }

        if (!userTagsAreUnique(userTags)) {
            throw new DuplicateTagException();
        }
        internalList.setAll(tags);
        mapTags.clear();
        initDefaultTags();
        for (Tag newTag: tags) {
            mapTags.put(newTag.getTagName(), newTag);
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Tag> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * Finds a specific tag that has the given tag name.
     * @param tagName Name of the tag
     * @return Tag
     */
    public Tag find(String tagName) {
        return mapTags.get(tagName);
    }

    @Override
    public Iterator<Tag> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof seedu.address.model.tag.UniqueTagList // instanceof handles nulls
            && internalList.equals(((seedu.address.model.tag.UniqueTagList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public HashMap<String, Tag> getMapTags() {
        return mapTags;
    }

    /**
     * Returns true if {@code Tags} contains only unique UserTags.
     */
    private boolean userTagsAreUnique(List<UserTag> userTags) {
        for (int i = 0; i < userTags.size() - 1; i++) {
            for (int j = i + 1; j < userTags.size(); j++) {
                if (userTags.get(i).isSameTag(userTags.get(j))) {
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
        for (DefaultTagType defaultTagType: DefaultTagType.values()) {
            addTag(new DefaultTag(defaultTagType));
        }
    }
}
