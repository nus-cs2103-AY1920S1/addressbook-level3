package seedu.address.model;

/**
 * Represents any type of data whereby the objects must be unique in the system.
 */
public abstract class UniqueElement {
    /**
     * Returns true if both elements of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public abstract <T extends UniqueElement> boolean isSameElement(T otherElement);
}
