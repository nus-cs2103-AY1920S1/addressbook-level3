package seedu.address.model.group;

/**
 * Descriptor of a group for group construction.
 */
public class GroupDescriptor {

    private static final GroupName DEFAULT_GROUPNAME = GroupName.emptyGroupName();
    private static final GroupDescription DEFAULT_GROUPDESCRIPTION = GroupDescription.emptyDescription();
    private static final GroupRemark DEFAULT_GROUPREMARK = GroupRemark.emptyRemark();

    private GroupName groupName;
    private GroupRemark groupRemark;
    private GroupDescription groupDescription;

    public GroupDescriptor() {
        this.groupName = DEFAULT_GROUPNAME;
        this.groupRemark = DEFAULT_GROUPREMARK;
        this.groupDescription = DEFAULT_GROUPDESCRIPTION;
    }

    public GroupDescriptor(GroupName groupName, GroupDescription groupDescription, GroupRemark groupRemark) {
        this.groupName = groupName;
        this.groupDescription = groupDescription;
        this.groupRemark = groupRemark;
    }

    /**
     * Checks if any field has been edited.
     *
     * @return boolean
     */
    public boolean isAnyFieldEdited() {
        if (this.groupName.equals(DEFAULT_GROUPNAME)
                && this.groupRemark.equals(DEFAULT_GROUPREMARK)
                && this.groupDescription.equals(DEFAULT_GROUPDESCRIPTION)) {
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

    public GroupDescription getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(GroupDescription groupDescription) {
        this.groupDescription = groupDescription;
    }

    public GroupRemark getGroupRemark() {
        return groupRemark;
    }

    public void setGroupRemark(GroupRemark groupRemark) {
        this.groupRemark = groupRemark;
    }
}
