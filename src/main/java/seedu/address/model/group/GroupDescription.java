package seedu.address.model.group;

/**
 * Description of the Group.
 */
public class GroupDescription {

    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public static final String MESSAGE_CONSTRAINTS =
            "Group Descriptions should only contain alphanumeric characters and spaces, and it should not be blank";

    public final String description;


    public GroupDescription(String description) {
        this.description = description;
    }

    private GroupDescription() {
        description = "";
    }

    public static GroupDescription emptyDescription() {
        return new GroupDescription();
    }

    @Override
    public String toString() {
        return description;
    }

    public static boolean isValid(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Checks if this GroupDescription is equal to other GroupDescription.
     *
     * @param other to be compared
     * @return boolean
     */
    public boolean equals(GroupDescription other) {
        if (other == null) {
            return false;
        } else if (other.toString().equals(this.description)) {
            return true;
        } else {
            return false;
        }
    }

}
