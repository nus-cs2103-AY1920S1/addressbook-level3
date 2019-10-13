package seedu.savenus.model.food;

/**
 * Represents a Simpler Interface for a Field of a Food.
 */
public interface Field extends Comparable<Field> {

    /**
     * Gets the field as a String.
     * @return a String representation of the field.
     */
    String getField();

    int compareTo(Field other);
}
