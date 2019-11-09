package seedu.address.model.group;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.group.exceptions.NoGroupFieldsEditedException;
import seedu.address.model.mapping.Role;

/**
 * List of Groups.
 */
public class GroupList {
    private ArrayList<Group> groups;

    public GroupList() {
        this.groups = new ArrayList<>();
    }

    /**
     * Adds a Group to the list of groups.
     *
     * @param groupDescriptor group to be added
     * @return true when successfully added group
     */
    public Group addGroup(GroupDescriptor groupDescriptor) throws DuplicateGroupException {
        try {
            findGroup(groupDescriptor.getGroupName());
            throw new DuplicateGroupException();

        } catch (GroupNotFoundException e) {
            Group group = new Group(groupDescriptor);
            this.groups.add(group);
            return group;
        }

    }

    public void addGroup(Group group) {
        this.groups.add(group);
    }

    /**
     * Deletes a group based on GroupId.
     *
     * @param groupId groupId to find the group to be deleted
     */
    public void deleteGroup(GroupId groupId) throws GroupNotFoundException {
        int i;
        for (i = 0; i < groups.size(); i++) {
            if (groups.get(i).getGroupId().equals(groupId)) {
                groups.remove(i);
                return;
            }
        }
        throw new GroupNotFoundException();
    }

    /**
     * Edits a group based on given GroupDescriptor.
     *
     * @param groupName       of group to be edited
     * @param groupDescriptor how the group should be edited
     * @return group
     */
    public Group editGroup(GroupName groupName, GroupDescriptor groupDescriptor)
            throws GroupNotFoundException, NoGroupFieldsEditedException, DuplicateGroupException {
        Group toEdit = findGroup(groupName);

        if (!groupDescriptor.isAnyFieldEdited()) {
            throw new NoGroupFieldsEditedException();
        }

        if (!groupDescriptor.getGroupName().equals(GroupName.emptyGroupName())) {
            GroupName otherName = groupDescriptor.getGroupName();
            try {
                findGroup(otherName);
                throw new DuplicateGroupException();
            } catch (GroupNotFoundException e) {
                e.printStackTrace();
            }
            toEdit.setGroupName(groupDescriptor.getGroupName());
        }

        if (!groupDescriptor.getGroupDescription().equals(GroupDescription.emptyDescription())) {
            toEdit.setGroupDescription(groupDescriptor.getGroupDescription());
        }

        if (!groupDescriptor.getUserRole().equals(Role.emptyRole())) {
            toEdit.setUserRole(groupDescriptor.getUserRole());
        }

        return toEdit;
    }

    /**
     * Finds a group with the same GroupName and returns the Group.
     *
     * @param groupName GroupName of the Group
     * @return Group that is found
     */
    public Group findGroup(GroupName groupName) throws GroupNotFoundException {
        int i;
        for (i = 0; i < groups.size(); i++) {
            if (groups.get(i).getGroupName().toString().equals(groupName.toString())) {
                return groups.get(i);
            }
        }
        throw new GroupNotFoundException();
    }

    /**
     * Finds a group with the same GroupId and returns the Group.
     *
     * @param groupId GroupId of the Group
     * @return Group that is found
     */
    public Group findGroup(GroupId groupId) throws GroupNotFoundException {
        int i;
        for (i = 0; i < groups.size(); i++) {
            if (groups.get(i).getGroupId().equals(groupId)) {
                return groups.get(i);
            }
        }
        throw new GroupNotFoundException();
    }

    /**
     * Retrun an unmodifiable observable list of Groups.
     *
     * @return ObservableList
     */
    public ObservableList<Group> asUnmodifiableObservableList() {
        ObservableList<Group> observableList = FXCollections.observableArrayList(groups);
        return FXCollections.unmodifiableObservableList(observableList);
    }

    /**
     * Returns an ArrayList of Groups.
     *
     * @return ArrayList
     */
    public ArrayList<Group> getGroups() {
        return this.groups;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GroupList // instanceof handles nulls
                && groups.equals(((GroupList) other).getGroups()));
    }
}
