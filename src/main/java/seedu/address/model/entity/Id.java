package seedu.address.model.entity;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

import seedu.address.commons.exceptions.IllegalValueException;


/**
 * Represents a Identification number in the address book.
 */
public class Id {

    //Constants
    public static final String MESSAGE_CONSTRAINTS_INVALID_ID = "Given Id string is invalid";
    public static final String MESSAGE_CONSTRAINTS_INVALID_NUMBER = "Number should be of the format integer,"
            + "and adhere to the following constraints: \n"
            + "1. It should  number which can be any digit."
            + "The number must: \n"
            + "    -be at least one digit long\n"
            + "    -contain only digits from 0 to 9\n";


    private static final String NUMBER_REGEX = "^[1-9]\\d*$";
    private static final String ID_REGEX = "^(?i)(M-|P-|T-)(?-i)[1-9]\\d*$";

    // Data fields
    private PrefixType prefix;
    private final int number;

    /**
     * Constructs an {@code Id}.
     *
     * @param prefix PrefixType to indicate type of entity.
     * @param number Index number of entity.
     */
    public Id(PrefixType prefix, int number) {
        requireNonNull(number);
        checkArgument(isValidNumber(number), MESSAGE_CONSTRAINTS_INVALID_NUMBER);
        this.prefix = prefix;
        this.number = number;

    }

    /**
     * Returns if number is a valid number.
     *
     * @param number Number.
     * @return boolean Whether number is in valid format.
     */
    public static boolean isValidNumber(int number) {
        return Integer.toString(number).matches(NUMBER_REGEX);
    }

    /**
     * Checks if given {@code strId} is a valid {@code String} to be passed onto {@link #toId(String)}.
     */
    public static boolean isValidString(String strId) {
        return strId.matches(ID_REGEX);
    }

    public PrefixType getPrefix() {
        return prefix;
    }

    public int getNumber() {
        return number;
    }

    public void setPrefix(PrefixType prefix) {
        this.prefix = prefix;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.prefix, this.number);
    }

    /**
     * Returns true if both Id objects have the same data fields(Prefix and number)..
     * This defines a stronger notion of equality between two Id object.
     *
     * @param other Other Id object.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Id)) {
            return false;
        }

        Id otherId = ((Id) other);
        return otherId.getPrefix().equals(this.getPrefix())
                && otherId.getNumber() == this.getNumber();
    }

    /**
     * Returns string representation of object.
     *
     * @return Id in string format.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getPrefix())
                .append("-")
                .append(getNumber());
        return builder.toString();
    }

    /**
     * Returns a deep copy of the Id object
     * @return a deep copy of the Id object
     */
    public Id copy() {
        return new Id(this.prefix, this.number);
    }

    /**
     * Converts given {@code strId} into an {@code Id}.
     *
     * @throws IllegalValueException If given {@code strId} does not match a valid {@code Id} format.
     */
    public static Id toId(String strId) throws IllegalValueException {
        if (!strId.matches(ID_REGEX)) {
            throw new IllegalValueException(MESSAGE_CONSTRAINTS_INVALID_ID);
        }
        // Can use valueOf() because given strId already matches ID_REGEX
        PrefixType prefixType = PrefixType.valueOf(strId.substring(0, 1).toUpperCase());
        return new Id(prefixType, Integer.parseInt(strId.substring(2)));
    }

}

