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

    public Group(GroupId groupId, GroupName groupName, GroupRemark groupRemark) {
        this.groupName = groupName;
        this.groupRemark = groupRemark;
        this.groupId = groupId;
    }

    public static void setCounter(int i) {
        counter = i;
    }

    /**
     * Reset counter for testing purposes.
     */
    public static void counterReset() {
        counter = 0;
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
     *
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

    /**
     * Checks if other group has the same details.
     *
     * @param other group to be compared
     * @return boolean
     */
    public boolean isSameGroup(Group other) {
        if (other == null) {
            return false;
        } else if (!other.getGroupRemark().equals(this.groupRemark)) {
            return false;
        } else if (!other.getGroupName().equals(this.groupName)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks if other group is same as this group.
     *
     * @param other group to be compared
     * @return boolean
     */
    public boolean equals(Group other) {
        if (other == null) {
            return false;
        } else if (!other.getGroupId().equals(this.groupId)) {
            return false;
        } else if (!this.isSameGroup(other)) {
            return false;
        } else {
            return true;
        }
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
