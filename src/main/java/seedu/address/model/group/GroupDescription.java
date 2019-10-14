package seedu.address.model.group;

public class GroupDescription {
    public final String description;

    public GroupDescription (String description) {
        this.description = description;
    }

    private GroupDescription() {
        description = "";
    }

    @Override
    public String toString() {
        return description;
    }

    public static GroupDescription emptyDescription() {
        return new GroupDescription();
    }

    /**
     * Checks if this GroupDescription is equal to other GroupDescription.
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
