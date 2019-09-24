package seedu.address.model.mapping;

import seedu.address.model.group.GroupId;
import seedu.address.model.person.PersonId;

/**
 * Maps a person to a group throught the PersonId and GroupId
 */
public class PersonToGroupMapping {
    private final GroupId groupId;
    private final PersonId personId;

    public PersonToGroupMapping(PersonId personId, GroupId groupId) {
        this.groupId = groupId;
        this.personId = personId;
    }

    public GroupId getGroupId() {
        return this.groupId;
    }

    public PersonId getPersonId() {
        return this.personId;
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
     * @param mapping to be compared
     * @return true if they are equivalent, false otherwise
     */
    public boolean equals(PersonToGroupMapping mapping) {
        if (mapping.getPersonId().equals(personId) && mapping.getGroupId().equals(groupId)) {
            return true;
        } else {
            return false;
        }
    }
}
