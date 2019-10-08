package seedu.exercise.model.exercise;

/**
 * Represents a property of an {@code Exercise} object.
 */
public abstract class Property {

    public abstract boolean equals(Object other);

    public abstract String toString();

    public abstract int hashCode();
}
