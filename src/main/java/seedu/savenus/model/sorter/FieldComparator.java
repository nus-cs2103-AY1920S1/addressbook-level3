package seedu.savenus.model.sorter;

import java.util.Comparator;

import seedu.savenus.model.food.Field;

/**
 * A Simple Comparator which compares two fields.
 */
public class FieldComparator implements Comparator<Field> {
    @Override
    public int compare(Field field, Field other) {
        return field.compareTo(other);
    }
}
