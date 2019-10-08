package seedu.address.model.group;

/**
 * Descriptor of a group for group construction.
 */
public class GroupDescriptor {

    private static final GroupRemark DEFAULT_GROUPREMARK = GroupRemark.emptyRemark();
    private static final GroupName DEFAULT_GROUPNAME = GroupName.emptyGroupName();

    private GroupName groupName;
    private GroupRemark groupRemark;

    public GroupDescriptor() {
        this.groupName = DEFAULT_GROUPNAME;
        this.groupRemark = DEFAULT_GROUPREMARK;
    }

    public GroupDescriptor(GroupName groupName, GroupRemark groupRemark) {
        this.groupName = groupName;
        this.groupRemark = groupRemark;
    }

    /**
     * Checks if any field has been edited.
     *
     * @return boolean
     */
    public boolean isAnyFieldEdited() {
        if (this.groupName.equals(DEFAULT_GROUPNAME) && this.groupRemark.equals(DEFAULT_GROUPREMARK)) {
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
                && this.groupRemark.equals(groupDescriptor.groupRemark)) {
            return true;
        } else {
            return false;
        }
    }

    public GroupName getGroupName() {
        return groupName;
    }

    public void setGroupName(GroupName groupName) {
        this.groupName = groupName;
    }

    public GroupRemark getGroupRemark() {
        return groupRemark;
    }

    public void setGroupRemark(GroupRemark groupRemark) {
        this.groupRemark = groupRemark;
    }
}
