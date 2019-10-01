package seedu.address.model.tag;

import java.util.HashSet;

/**
 * Represents a storage for default and user-created tags.
 */
public class TagList {

    private HashSet<Default> defaultTags;
    private HashSet<UserTag> userTags;

    /**
     * Constructs a {@code TagList} with existing set of user-created tags.
     * @param userTags A set of user-created tags.
     */
    public TagList(HashSet<UserTag> userTags) {
        initialiseDefaultTags();
        this.userTags = userTags;
    }

    /**
     * Constructs a {@code TagList} with an empty set of user-created tags.
     */
    public TagList() {
        initialiseDefaultTags();
        this.userTags = new HashSet<UserTag>();
    }

    /**
     * Adds a user-created tag to the {@code TagList}.
     * @param userTag The tag created by the user.
     */
    public void addUserTag(UserTag userTag) {
        userTags.add(userTag);
    }

    /**
     * Removes a user-created tag from the {@code TagList}
     * @param userTag The user-created tag to be removed.
     * @throws IllegalArgumentException If the user-created tag is not in the {@code TagList}.
     */
    public void deleteUserTag(UserTag userTag) throws IllegalArgumentException {
        if (!containsUserTag(userTag)) {
            throw new IllegalArgumentException("TagList does not contain " + userTag);
        }
        userTags.remove(userTag);
    }

    /**
     * Checks if the {@code TagList} contains the specified user-created tag.
     * @param userTag The user-created tag to be checked.
     * @return True if it is in the {@code TagList}.
     */
    public boolean containsUserTag(UserTag userTag) {
        return defaultTags.contains(userTag);
    }

    /**
     * Returns the existing {@code UserTag} in the {@code TagList} based on another equivalent {@code UserTag} object.
     * @param userTag The equivalent {@code UserTag} object.
     * @return The existing {@code UserTag}, if present.
     * @throws IllegalArgumentException If there is no equivalent existing user-created tag.
     */
    public UserTag getExistingUserTag(UserTag userTag) throws IllegalArgumentException {
        for (UserTag existingUserTag : userTags) {
            if (existingUserTag.equals(userTag)) {
                return existingUserTag;
            }
        }
        throw new IllegalArgumentException("TagList does not contain " + userTag);
    }

    private void initialiseDefaultTags() {
        for (Default defaultTag: Default.values()) {
            defaultTags.add(defaultTag);
        }
    }

}
