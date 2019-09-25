package seedu.address.model.group;

/**
 * Descriptor of a group for group construction.
 */
public class GroupDescriptor {
    private GroupName groupName;
    private GroupRemark groupRemark;

    public GroupDescriptor() {
        this.groupName = null;
        this.groupRemark = null;
    }

    /**
     * Checks if any field has been edited.
     * @return boolean
     */
    public boolean isAnyFieldEdited() {
        if (this.groupName == null && this.groupRemark == null) {
            return false;
        } else {
            return true;
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
