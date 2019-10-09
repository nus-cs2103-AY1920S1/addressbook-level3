package seedu.address.model.vehicle;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's VehicleNumber in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidVehicleNumber(String)}
 */
public class VehicleNumber {

    public static final String MESSAGE_CONSTRAINTS =
            "Vehicle Numbers should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String vehicleNumber;

    /**
     * Constructs a {@code VehicleNumber}.
     *
     * @param number A valid VehicleNumber.
     */
    public VehicleNumber(String number) {
        requireNonNull(number);
        checkArgument(isValidVehicleNumber(number), MESSAGE_CONSTRAINTS);
        vehicleNumber = number;
    }

    /**
     * Returns true if a given string is a valid VehicleNumber.
     */
    public static boolean isValidVehicleNumber(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return vehicleNumber;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof VehicleNumber // instanceof handles nulls
                && vehicleNumber.equals(((VehicleNumber) other).vehicleNumber)); // state check
    }

    @Override
    public int hashCode() {
        return vehicleNumber.hashCode();
    }

}

