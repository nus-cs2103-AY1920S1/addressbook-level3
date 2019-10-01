package seedu.address.model.group;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
     *
     * @param groupDescriptor group to be added
     * @return true when successfully added group
     */
    public Group addGroup(GroupDescriptor groupDescriptor) {
        if (findGroup(groupDescriptor.getGroupName()) == null) {
            Group group = new Group(groupDescriptor);
            this.groups.add(group);
            return group;
        } else {
            return null;
        }
    }

    public void addGroup(Group group) {
        this.groups.add(group);
    }

    /**
     * Deletes a group based on GroupId.
     *
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

    /**
     * Edits a group based on given GroupDescriptor.
     *
     * @param groupName       of group to be edited
     * @param groupDescriptor how the group should be edited
     * @return group
     */
    public Group editGroup(GroupName groupName, GroupDescriptor groupDescriptor) {
        Group toEdit = findGroup(groupName);

        if (groupDescriptor.getGroupName() != null) {
            GroupName otherName = groupDescriptor.getGroupName();
            if (findGroup(otherName) != null) {
                return null;
            }
            toEdit.setGroupName(groupDescriptor.getGroupName());
        }

        if (groupDescriptor.getGroupRemark() != null) {
            toEdit.setGroupRemark(groupDescriptor.getGroupRemark());
        }

        return toEdit;
    }

    /**
     * Finds a group with the same GroupName and returns the Group.
     *
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
     *
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
     *
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
     * Retrun an unmodifiable observable list of Groups.
     * @return ObservableList
     */
    public ObservableList<Group> asUnmodifiableObservableList() {
        ObservableList<Group> observableList = FXCollections.observableArrayList(groups);
        return FXCollections.unmodifiableObservableList(observableList);
    }

    /**
     * Returns an ArrayList of Groups.
     * @return ArrayList
     */
    public ArrayList<Group> getGroups() {
        return this.groups;
    }

}
