package seedu.address.model.entity;

import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Location {
    private final int tableNumber;

    public static final String MESSAGE_CONSTRAINTS_INVALID_TABLE_NUMBER = "Table number should be an integer"
            + "and adhere to the following constraints:\n"
            + "1. The tableNumber can be any digit.\n"
            + "The tableNumber must:\n"
            + "    -be at least one digit long\n"
            + "    -contain only digits from 0 to 9\n";

    private static final String TABLE_NUMBER_REGEX = "^\\d+$";


    /**
     * Constructor without team.
     * Constructs an {@code Location}.
     *
     * @param tableNumber Table that team is seated on.
     */
    public Location(int tableNumber) {
        requireNonNull(tableNumber);
        checkArgument(isValidNumber(tableNumber), MESSAGE_CONSTRAINTS_INVALID_TABLE_NUMBER);
        this.tableNumber = tableNumber;
    }

    /**
     * Returns if a given tableNumber is a valid number.
     *
     * @param tableNumber tableNumber that team is seated on.
     * @return boolean whether test is in valid tableNumber format.
     */
    public static boolean isValidNumber(int tableNumber){
        return Integer.toString(tableNumber).matches(TABLE_NUMBER_REGEX);
    }

    // Getter

    public int getTableNumber() {
        return tableNumber;
    }


    // Setter


    @Override
    public int hashCode() {
        return Objects.hash(this.tableNumber);
    }

    @Override
    public boolean equals(Object other) {
        Location otherLocation = ((Location) other);
        return otherLocation == this |
                otherLocation.getTableNumber() == this.getTableNumber();
    }

    /**
     * Returns string representation of object.
     *
     * @return Location in string format.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(" Table Number: ")
                .append(getTableNumber());
        return builder.toString();
    }

    /**
     * Returns string representation of object, for storage.
     *
     * @return Location in string format.
     */
    public int toStorageValue(){
        return this.tableNumber;
    }
}
