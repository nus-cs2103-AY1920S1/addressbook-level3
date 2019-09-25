package seedu.address.model.group;

import java.util.ArrayList;

/**
 * List of Groups.
 */
public class GroupList {
    private ArrayList<Group> groups;

    public GroupList() {
        this.groups = new ArrayList<Group>();
    }

    /**
     * Adds a Group to the list of groups.
     * @param groupDescriptor group to be added
     * @return true when successfully added group
     */
    public boolean addGroup(GroupDescriptor groupDescriptor) {
        if (findGroup(groupDescriptor.getGroupName()) == null) {
            this.groups.add(new Group(groupDescriptor));
            return true;
        } else {
            return false;
        }
    }

    /**
     * Finds a group with the same GroupName and returns the Group.
     * @param groupName GroupName of the Group
     * @return Group that is found
     */
    public Group findGroup(GroupName groupName) {
        int i;
        for (i = 0; i < groups.size(); i++) {
            if (groups.get(i).getGroupName().toString().equals(groupName.toString())) {
                return groups.get(i);
            }
        }
        return null;
    }

    /**
     * Finds a group with the same GroupId and returns the Group.
     * @param groupId GroupId of the Group
     * @return Group that is found
     */
    public Group findGroup(GroupId groupId) {
        int i;
        for (i = 0; i < groups.size(); i++) {
            if (groups.get(i).getGroupId().equals(groupId)) {
                return groups.get(i);
            }
        }
        return null;
    }

    /**
     * Converts to String.
     * @return String
     */
    public String toString() {
        int i;
        String output = "";
        for (i = 0; i < groups.size(); i++) {
            output += groups.get(i).toString();
            output += "\n";
        }
        return output;
    }

    /**
     * Deletes a group based on GroupId.
     * @param groupId groupId to find the group to be deleted
     * @return true when successfully deleted group
     */
    public boolean deleteGroup(GroupId groupId) {
        int i;
        for (i = 0; i < groups.size(); i++) {
            if (groups.get(i).getGroupId().equals(groupId)) {
                groups.remove(i);
                return true;
            }
        }
        return false;
    }
}
