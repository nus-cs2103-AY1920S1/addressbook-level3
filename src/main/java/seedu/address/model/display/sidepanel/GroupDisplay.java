package seedu.address.model.display.sidepanel;

import seedu.address.model.group.Group;
import seedu.address.model.group.GroupDescription;
import seedu.address.model.group.GroupName;
import seedu.address.model.group.GroupRemark;

/**
 * Group display model
 */
public class GroupDisplay extends Display {
    private final GroupName groupName;
    private final GroupDescription groupDescription;
    private final GroupRemark groupRemark;

    public GroupDisplay(GroupName groupName, GroupRemark groupRemark, GroupDescription groupDescription) {
        this.groupName = groupName;
        this.groupDescription = groupDescription;
        this.groupRemark = groupRemark;
    }

    public GroupDisplay(Group group) {
        this.groupName = group.getGroupName();
        this.groupDescription = group.getGroupDescription();
        this.groupRemark = group.getGroupRemark();
    }

    public GroupName getGroupName() {
        return groupName;
    }

    public GroupRemark getGroupRemark() {
        return groupRemark;
    }

    public GroupDescription getGroupDescription() {
        return groupDescription;
    }
}
