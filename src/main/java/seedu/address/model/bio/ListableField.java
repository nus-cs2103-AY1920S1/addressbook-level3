package seedu.address.model.bio;

/**
 * A field type that contains multiple values which can be represented in the form of a list.
 * E.g. A diabetic patient can have multiple medical conditions.
 */
public interface ListableField {

    public boolean equals(Object other);

    public int hashCode();

    public String toString();

}
