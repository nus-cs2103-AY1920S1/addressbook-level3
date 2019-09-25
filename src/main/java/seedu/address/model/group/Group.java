package seedu.address.model.group;

/**
 * A group.
 */
public class Group {
    private static Integer counter = 0;
    private final GroupId groupId;
    private GroupName groupName;
    private GroupRemark groupRemark;

    public Group(GroupDescriptor groupDescriptor) {
        this.groupName = groupDescriptor.getGroupName();
        this.groupRemark = groupDescriptor.getGroupRemark();
        this.groupId = new GroupId(counter);
        counter += 1;
    }

    /**
     * Converts to String.
     *
     * @return String
     */
    public String toString() {
        String output = "";
        output += "(" + groupId.toString() + ") ";
        output += groupName.toString();
        return output;
    }

    /**
     * Prints out all details of the group.
     * @return String
     */
    public String details() {
        String output = "";
        String notAvailable = "NOT AVAILABLE";
        output += this.toString() + "\n";

        output += "Description: ";
        if (groupRemark == null) {
            output += notAvailable + "\n";
        } else {
            output += groupRemark.toString() + "\n";
        }
        output += "\n";

        return output;
    }

    public GroupRemark getGroupRemark() {
        return groupRemark;
    }

    public void setGroupRemark(GroupRemark groupRemark) {
        this.groupRemark = groupRemark;
    }

    public GroupName getGroupName() {
        return this.groupName;
    }

    public void setGroupName(GroupName groupName) {
        this.groupName = groupName;
    }

    public GroupId getGroupId() {
        return this.groupId;
    }


}
