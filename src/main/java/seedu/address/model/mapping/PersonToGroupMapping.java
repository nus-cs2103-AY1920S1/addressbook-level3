package seedu.address.model.mapping;

import seedu.address.model.group.GroupId;
import seedu.address.model.person.PersonId;

/**
 * Maps a person to a group throught the PersonId and GroupId
 */
public class PersonToGroupMapping {
    private final GroupId groupId;
    private final PersonId personId;
    private Role role;

    public PersonToGroupMapping(PersonId personId, GroupId groupId) {
        this.groupId = groupId;
        this.personId = personId;
        this.role = Role.emptyRole();
    }

    public PersonToGroupMapping(PersonId personId, GroupId groupId, Role role) {
        this.groupId = groupId;
        this.personId = personId;
        this.role = role;
    }

    public GroupId getGroupId() {
        return this.groupId;
    }

    public PersonId getPersonId() {
        return this.personId;
    }

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Converts to String.
     * @return String
     */
    public String toString() {
        String s = "MAP: " + personId.toString() + " - " + groupId.toString();
        return s;
    }

    /**
     * Checks if the mapping is equivalent to another mapping.
     * @param other to be compared
     * @return true if they are equivalent, false otherwise
     */
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else {
            PersonToGroupMapping mapping = null;
            if (other instanceof PersonToGroupMapping) {
                mapping = (PersonToGroupMapping) other;
            }
            if (mapping != null) {
                return mapping.getPersonId().equals(personId) && mapping.getGroupId().equals(groupId);
            }
        }
        return false;
    }
}
