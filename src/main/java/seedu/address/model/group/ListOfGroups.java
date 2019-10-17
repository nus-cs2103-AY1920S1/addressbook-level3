package seedu.address.model.group;

import java.util.ArrayList;

/**
 * Model that represents a list of groups.
 */
public class ListOfGroups {

    private ArrayList<Group> groups;

    /**
     * Creates a ListOfGroups instance with the appropriate attributes.
     */
    public ListOfGroups() {
        groups = new ArrayList<>();
    }

    /**
     * Adds a group to the list of groups.
     * @param group The group to be added to the list of groups.
     */
    public void addGroup(Group group) {
        groups.add(group);
    }

    /**
     * Removes a group from the list of groups.
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
     * @param groupIndex The group index.
     * @return The group from the list of groups.
     */
    public Group getGroup(int groupIndex) {
        return groups.get(groupIndex);
    }
}
