package seedu.address.model.incident;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's CallerNumber in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCallerNumber(String)}
 */
public class CallerNumber {

    public static final String MESSAGE_CONSTRAINTS =
            "Caller numbers should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String callerNumber;

    /**
     * Constructs a {@code VehicleNumber}.
     *
     * @param callerNumber A valid VehicleNumber.
     */
    public CallerNumber(String callerNumber) {
        requireNonNull(callerNumber);
        checkArgument(isValidCallerNumber(callerNumber), MESSAGE_CONSTRAINTS);
        this.callerNumber = callerNumber;
    }

    /**
     * Returns true if a given string is a valid VehicleNumber.
     */
    public static boolean isValidCallerNumber(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return callerNumber;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CallerNumber // instanceof handles nulls
                && callerNumber.equals(((CallerNumber) other).callerNumber)); // state check
    }

    @Override
    public int hashCode() {
        return callerNumber.hashCode();
    }

}

