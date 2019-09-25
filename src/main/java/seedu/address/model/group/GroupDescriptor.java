package seedu.address.model.group;

public class GroupDescriptor {
    private GroupName groupName;
    private GroupRemark groupRemark;

    public GroupDescriptor(){
        this.groupName = null;
        this.groupRemark = null;
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
