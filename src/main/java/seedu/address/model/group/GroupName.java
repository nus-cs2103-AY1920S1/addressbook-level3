package seedu.address.model.group;

/**
 * Name of the Group
 */
public class GroupName {

    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public static final String MESSAGE_CONSTRAINTS =
            "Group Names should only contain alphanumeric characters and spaces, and it should not be blank";

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

    public static boolean isValid(String test) {
        return test.matches(VALIDATION_REGEX);
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
