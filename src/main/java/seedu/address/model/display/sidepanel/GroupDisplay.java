package seedu.address.model.display.sidepanel;

import seedu.address.model.group.Group;
import seedu.address.model.group.GroupDescription;
import seedu.address.model.group.GroupName;

/**
 * Group display model
 */
public class GroupDisplay extends Display {
    private final GroupName groupName;
    private final GroupDescription groupDescription;

    public GroupDisplay(GroupName groupName, GroupDescription groupDescription) {
        this.groupName = groupName;
        this.groupDescription = groupDescription;
    }

    public GroupDisplay(Group group) {
        this.groupName = group.getGroupName();
        this.groupDescription = group.getGroupDescription();
    }

    public GroupName getGroupName() {
        return groupName;
    }

    public GroupDescription getGroupDescription() {
        return groupDescription;
    }
}
