package seedu.address.model.group;

/**
 * Description of the Group.
 */
public class GroupDescription {
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
