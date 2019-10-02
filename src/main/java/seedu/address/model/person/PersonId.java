package seedu.address.model.person;

/**
 * Identifier of a Person.
 */
public class PersonId {
    private final Integer identifier;

    public PersonId(Integer id) {
        this.identifier = id;
    }

    public PersonId(String id) {
        this.identifier = Integer.parseInt(id);
    }

    public Integer getIdentifier() {
        return this.identifier;
    }

    public String toString() {
        return identifier.toString();
    }

    /**
     * Checks if other personId is equal.
     * @param id to be compared
     * @return true if they are equal
     */
    public boolean equals(PersonId id) {
        if (identifier.intValue() == id.getIdentifier().intValue()) {
            return true;
        } else {
            return false;
        }
    }
}
