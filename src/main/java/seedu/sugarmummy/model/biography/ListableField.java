package seedu.sugarmummy.model.biography;

/**
 * A field type that contains multiple values which can be represented in the form of a list. E.g. A diabetic patient
 * can have multiple medical conditions.
 */
public interface ListableField {

    boolean equals(Object other);

    int hashCode();

    String toString();

}
