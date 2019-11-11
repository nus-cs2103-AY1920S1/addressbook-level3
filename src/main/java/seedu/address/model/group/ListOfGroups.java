package seedu.address.model.group;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.group.exceptions.GroupNotFoundException;

/**
 * Model that represents a list of groups.
 */
public class ListOfGroups {

    private static String currentlyQueriedGroup;
    private ArrayList<Group> groups;

    /**
     * Creates a ListOfGroups instance with the appropriate attributes.
     */
    public ListOfGroups() {
        groups = new ArrayList<>();
    }

    /**
     * Sets the currently queried group
     */
    public static void setCurrentlyQueriedGroup(String queriedGroup) {
        currentlyQueriedGroup = queriedGroup;
    }

    /**
     * Gets the currently queried group
     *
     * @return the currently queried group
     */
    public static String getCurrentlyQueriedGroup() {
        return currentlyQueriedGroup;
    }

    /**
     * Adds a group to the list of groups.
     *
     * @param group The group to be added to the list of groups.
     */
    public void addGroup(Group group) {
        if (groups.contains(group)) {
            throw new DuplicateGroupException();
        }
        groups.add(group);
    }

    /**
     * Removes a group from the list of groups.
     *
     * @param groupId The group to be removed from the list of groups.
     */
    public void removeGroup(String groupId) {
        boolean removed = false;
        for (Group q : groups) {
            if (q.getGroupId().equals(groupId)) {
                groups.remove(q);
                removed = true;
                break;
            }
        }
        if (!removed) {
            throw new GroupNotFoundException();
        }
    }

    /**
     * Returns the groupIndex of a group if found, else -1.
     *
     * @param groupId The identifier of the group.
     * @return The groupIndex of the group.
     */
    public int getGroupIndex(String groupId) {
        for (int i = 0; i < groups.size(); i++) {
            Group currentGroup = groups.get(i);
            if (currentGroup.getGroupId().equals(groupId)) {
                return i;
            }
        }
        throw new GroupNotFoundException();
    }

    /**
     * Returns a group from the list of groups.
     *
     * @param groupIndex The group index.
     * @return The group from the list of groups.
     */
    public Group getGroup(int groupIndex) {
        return groups.get(groupIndex);
    }

    /**
     * Checks if group exists in the list of groups
     */
    public boolean contains(Group group) {
        return groups.contains(group);
    }

    /**
     * Returns array list of groups.
     *
     * @return array list of groups.
     */
    public ArrayList<Group> getGroupList() {
        return this.groups;
    }

    /**
     * Exports the group specified in the parameters of the method.
     *
     * @param groupId Id of group to be exported
     */
    public void exportGroup(String groupId) {
        Group queriedGroup = null;
        boolean queriedGroupFound = false;
        for (Group group : groups) {
            if (groupId.equals(group.getGroupId())) {
                queriedGroup = group;
                queriedGroupFound = true;
                break;
            }
        }
        if (queriedGroupFound) {
            queriedGroup.export();
        } else {
            throw new GroupNotFoundException();
        }
    }

    /**
     * Set all groups in the List Of Groups
     */
    public void setGroups(List<Group> groupList) {
        if (!groupsAreUnique(groupList)) {
            throw new DuplicateGroupException();
        }
        this.groups.clear();
        this.groups.addAll(groupList);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                // short circuit if same object
                || (other instanceof ListOfGroups // instanceof handles nulls
                && groups.equals(((ListOfGroups) other).groups));
    }

    /**
     * Returns true if {@code Group} contains only unique groups.
     */
    private boolean groupsAreUnique(List<Group> groups) {
        for (int i = 0; i < groups.size() - 1; i++) {
            for (int j = i + 1; j < groups.size(); j++) {
                if (groups.get(i).equals(groups.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
