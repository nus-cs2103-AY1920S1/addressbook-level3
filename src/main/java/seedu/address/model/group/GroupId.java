package seedu.address.model.group;

/**
 * Identifier of a Group.
 */
public class GroupId {
    private final Integer identifier;

    public GroupId(Integer id) {
        this.identifier = id;
    }

    public GroupId(String id) {
        this.identifier = Integer.parseInt(id);
    }

    public Integer getIdentifier() {
        return identifier;
    }

    public String toString() {
        return identifier.toString();
    }

    /**
     * Checks if this GroupId is equivalent to another.
     * @param id other GroupId
     * @return true is they are equivalent, false otherwise
     */
    public boolean equals(GroupId id) {
        if (identifier.intValue() == id.getIdentifier().intValue()) {
            return true;
        } else {
            return false;
        }
    }

}
