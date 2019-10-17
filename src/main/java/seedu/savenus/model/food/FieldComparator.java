package seedu.savenus.model.food;

import java.util.Comparator;

/**
 * A Simple Comparator which compares two fields.
 */
public class FieldComparator implements Comparator<Field> {
    @Override
    public int compare(Field field, Field other) {
        return field.compareTo(other);
    }
}
