package seedu.address.model.group;

/**
 * Name of the Group
 */
public class GroupName {
    private String groupName;

    public GroupName(String groupName) {
        this.groupName = groupName;
    }

    private GroupName() {
        groupName = "";
    }

    public String toString() {
        return this.groupName;
    }

    public static GroupName emptyGroupName() {
        return new GroupName();
    }

    /**
     * Checks if this GroupName is equal to other GroupName.
     * @param other to be compared
     * @return boolean
     */
    public boolean equals(GroupName other) {
        if (other == null) {
            return false;
        } else if (other.toString().equals(this.groupName)) {
            return true;
        } else {
            return false;
        }
    }
}
