package seedu.address.model.book;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Book's serial number in the catalog.
 * Guarantees: immutable; is valid as declared in {@link #isValidSerialNumber(String)}
 */
public class SerialNumber implements Comparable<SerialNumber> {

    public static final String MESSAGE_CONSTRAINTS =
            "Serial numbers should start with prefix \"B\", followed by 5 digits. They should be unique.";
    public static final String VALIDATION_REGEX = "B\\d{5}";
    public final String value;

    /**
     * Constructs a {@code SerialNumber}.
     *
     * @param serialNumber A valid serial number.
     */
    public SerialNumber(String serialNumber) {
        requireNonNull(serialNumber);
        checkArgument(isValidSerialNumber(serialNumber), MESSAGE_CONSTRAINTS);
        value = serialNumber;
    }

    /**
     * Returns true if a given string is a valid serial number.
     */
    public static boolean isValidSerialNumber(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SerialNumber // instanceof handles nulls
                && value.equals(((SerialNumber) other).value)); // state check
    }

    @Override
    public int compareTo(SerialNumber b) {
        return Integer.compare(
                Integer.parseInt(this.toString().substring(1)), Integer.parseInt(b.toString().substring(1))
        );
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
