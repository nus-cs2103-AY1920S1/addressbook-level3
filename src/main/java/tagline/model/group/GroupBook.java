//@@author e0031374
package tagline.model.group;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

/**
 * Wraps all data at the group-book level
 * Duplicates are not allowed (by .isSameGroup comparison)
 */
public class GroupBook implements ReadOnlyGroupBook {

    private final UniqueGroupList groups;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        groups = new UniqueGroupList();
    }

    public GroupBook() {}

    /**
     * Creates an GroupBook using the Groups in the {@code toBeCopied}
     */
    public GroupBook(ReadOnlyGroupBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the group list with {@code groups}.
     * {@code groups} must not contain duplicate groups.
     */
    public void setGroups(List<Group> groups) {
        this.groups.setGroups(groups);
    }

    /**
     * Resets the existing data of this {@code GroupBook} with {@code newData}.
     */
    public void resetData(ReadOnlyGroupBook newData) {
        requireNonNull(newData);

        setGroups(newData.getGroupList());
    }

    //// group-level operations

    /**
     * Returns true if a group with the same identity as {@code group} exists in the address book.
     */
    public boolean hasGroup(Group group) {
        requireNonNull(group);
        return groups.containsGroup(group);
    }

    /**
     * Returns a list of groups that contain the given member id as one of their members.
     */
    public List<Group> findGroupsWithMember(MemberId memberId) {
        requireNonNull(memberId);
        return groups.findGroupsWithMember(memberId);
    }

    /**
     * Returns true if a group with the same name as {@code groupName} exists in the address book.
     */
    public boolean hasGroupName(GroupName groupName) {
        requireNonNull(groupName);
        return groups.containsGroupName(groupName);
    }

    /**
     * Adds a group to the address book.
     * The group must not already exist in the address book.
     */
    public void addGroup(Group p) {
        groups.addGroup(p);
    }

    /**
     * Replaces the given group {@code target} in the list with {@code editedGroup}.
     * {@code target} must exist in the address book.
     * The group identity of {@code editedGroup} must not be the same as another existing group in the address book.
     */
    public void setGroup(Group target, Group editedGroup) {
        requireNonNull(editedGroup);

        groups.setGroup(target, editedGroup);
    }

    /**
     * Removes {@code key} from this {@code GroupBook}.
     * {@code key} must exist in the address book.
     */
    public void removeGroup(Group key) {
        groups.removeGroup(key);
    }

    //// util methods

    @Override
    public String toString() {
        return groups.asUnmodifiableObservableList().size() + " groups";
        // TODO: refine later
    }

    @Override
    public ObservableList<Group> getGroupList() {
        return groups.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GroupBook // instanceof handles nulls
                && groups.equals(((GroupBook) other).groups));
    }

    @Override
    public int hashCode() {
        return groups.hashCode();
    }
}
