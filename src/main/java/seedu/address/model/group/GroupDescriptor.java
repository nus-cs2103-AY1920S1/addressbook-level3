package seedu.address.model.group;

import seedu.address.model.mapping.Role;

/**
 * Descriptor of a group for group construction.
 */
public class GroupDescriptor {

    private static final GroupName DEFAULT_GROUPNAME = GroupName.emptyGroupName();
    private static final GroupDescription DEFAULT_GROUPDESCRIPTION = GroupDescription.emptyDescription();
    private static final Role DEFAULT_ROLE = Role.emptyRole();

    private GroupName groupName;
    private GroupDescription groupDescription;
    private Role userRole;

    public GroupDescriptor() {
        this.groupName = DEFAULT_GROUPNAME;
        this.groupDescription = DEFAULT_GROUPDESCRIPTION;
        this.userRole = DEFAULT_ROLE;
    }

    public GroupDescriptor(GroupName groupName,
                           GroupDescription groupDescription,
                           Role userRole) {

        this.groupName = groupName;
        this.groupDescription = groupDescription;
        this.userRole = userRole;
    }

    /**
     * Checks if any field has been edited.
     *
     * @return boolean
     */
    public boolean isAnyFieldEdited() {
        if (this.groupName.equals(DEFAULT_GROUPNAME)
                && this.groupDescription.equals(DEFAULT_GROUPDESCRIPTION)
                && this.userRole.equals(DEFAULT_ROLE)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Compares if this GroupDescriptor is equal to another.
     *
     * @param groupDescriptor to compare
     * @return boolean
     */
    public boolean equals(GroupDescriptor groupDescriptor) {
        if (groupDescriptor == null) {
            return false;
        } else if (this.groupName.equals(groupDescriptor.groupName)
                && this.groupDescription.equals(groupDescriptor.groupDescription)
                && this.userRole.equals(groupDescriptor.userRole)) {
            return true;
        } else {
            return false;
        }
    }

    public Role getUserRole() {
        return userRole;
    }

    public void setUserRole(Role userRole) {
        this.userRole = userRole;
    }

    public GroupName getGroupName() {
        return groupName;
    }

    public void setGroupName(GroupName groupName) {
        this.groupName = groupName;
    }

    public GroupDescription getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(GroupDescription groupDescription) {
        this.groupDescription = groupDescription;
    }
}
