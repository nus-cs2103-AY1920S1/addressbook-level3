package seedu.savenus.model.sort;

import java.util.Comparator;

import seedu.savenus.model.food.Field;

//@@author seanlowjk
/**
 * A Simple Comparator which compares two fields.
 */
public class FieldComparator implements Comparator<Field> {
    @Override
    public int compare(Field field, Field other) {
        return field.compareTo(other);
    }
}
