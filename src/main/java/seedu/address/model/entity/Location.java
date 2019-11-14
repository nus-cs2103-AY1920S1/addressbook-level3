package seedu.address.model.entity;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a seating location of an Entity in the address book. Guarantees:
 * details are present and not null, field values is validated, immutable.
 */
public class Location {

    // Constants
    public static final String MESSAGE_CONSTRAINTS_INVALID_TABLE_NUMBER = "Table number should be an integer "
            + "and adhere to the following constraint:\n"
            + "1. The tableNumber can be any number from 1 to 1000\n";

    // Data fields
    private final int tableNumber;

    /**
     * Constructs a {@code Location}.
     *
     * @param tableNumber Table that team is seated on.
     */
    public Location(int tableNumber) {
        checkArgument(isValidLocation(tableNumber), MESSAGE_CONSTRAINTS_INVALID_TABLE_NUMBER);
        this.tableNumber = tableNumber;
    }

    /**
     * Returns if a given number is a valid seating location.
     *
     * @param tableNumber Seating number of Entity.
     * @return boolean indicating whether the table number is valid.
     */
    public static boolean isValidLocation(int tableNumber) {
        return tableNumber >= 1 && tableNumber <= 1000;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.tableNumber);
    }

    /**
     * Returns true if both Location objects have the same data fields. This defines
     * a stronger notion of equality between two Location object.
     *
     * @param other Other Location object.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Location)) {
            return false;
        }
        Location otherLocation = ((Location) other);
        return otherLocation.getTableNumber() == this.getTableNumber();
    }

    /**
     * Returns string representation of object.
     *
     * @return Location in string format.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(" Table ").append(getTableNumber());
        return builder.toString();
    }

    /**
     * Returns string representation of object, for storage.
     *
     * @return Location in string format.
     */
    public int toStorageValue() {
        return this.tableNumber;
    }

    /**
     * Returns a deep copy of the Location object
     * @return a deep copy of the Location object
     */
    public Location copy() {
        return new Location(this.tableNumber);
    }
}
