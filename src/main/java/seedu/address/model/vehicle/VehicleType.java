package seedu.address.model.vehicle;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidVehicleType(String)}
 */
public class VehicleType {

    public static final String MESSAGE_CONSTRAINTS =
            "VehicleTypes should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String vehicleType;

    /**
     * Constructs a {@code Name}.
     *
     * @param type A valid vehicleType.
     */
    public VehicleType(String type) {
        requireNonNull(type);
        checkArgument(isValidVehicleType(type), MESSAGE_CONSTRAINTS);
        vehicleType = type;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidVehicleType(String vehicleType) {
        return vehicleType.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return vehicleType;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof VehicleType // instanceof handles nulls
                && vehicleType.equals(((VehicleType) other).vehicleType)); // state check
    }

    @Override
    public int hashCode() {
        return vehicleType.hashCode();
    }

}
