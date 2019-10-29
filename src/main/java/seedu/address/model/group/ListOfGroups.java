package seedu.address.model.group;

import java.util.ArrayList;

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
        groups.add(group);
    }

    /**
     * Removes a group from the list of groups.
     *
     * @param groupId The group to be removed from the list of groups.
     */
    public void removeGroup(String groupId) {
        for (Group q : groups) {
            if (q.getGroupId().equals(groupId)) {
                groups.remove(q);
            }
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
        return -1;
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
     * Returns array list of groups.
     *
     * @return array list of groups.
     */
    public ArrayList<Group> getGroupList() {
        return this.groups;
    }

    /**
     * Exports the group specified in the parameters of the method.
     * @param groupId Id of group to be exported
     */
    public void exportGroup(String groupId) {
        Group queriedGroup = null;
        for (Group group : groups) {
            if (groupId.equals(group.getGroupId())) {
                queriedGroup = group;
                break;
            }
        }
        queriedGroup.export();
    }
}
