package seedu.address.model.display.sidepanel;

import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.group.GroupRemark;

public class GroupDisplay extends Display {
    private final GroupName groupName;
    private final GroupRemark groupRemark;

    public GroupDisplay(GroupName groupName, GroupRemark groupRemark) {
        this.groupName = groupName;
        this.groupRemark = groupRemark;
    }

    public GroupDisplay(Group group) {
        this.groupName = group.getGroupName();
        this.groupRemark = group.getGroupRemark();
    }

    public GroupName getGroupName() {
        return groupName;
    }

    public GroupRemark getGroupRemark() {
        return groupRemark;
    }
}
