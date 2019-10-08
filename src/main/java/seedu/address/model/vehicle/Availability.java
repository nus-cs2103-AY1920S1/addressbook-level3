package seedu.address.model.vehicle;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a vehicle's availability in the IMS.
 * Guarantees: immutable; is valid as declared in {@link #isValidAvailability(String)}
 */
public class Availability {

    public static final String MESSAGE_CONSTRAINTS =
            "Availability is either 'AVAILABLE' or 'BUSY'";

    // Vehicle availability status tags
    public static final String VEHICLE_AVAILABLE_TAG = "AVAILABLE";
    public static final String VEHICLE_BUSY_TAG = "BUSY";

    private String availability;
    /**
     * Constructs an {@code Availability}.
     *
     * @param d A valid Availability tag.
     */
    public Availability(String d) {
        requireNonNull(d);
        checkArgument(isValidAvailability(d), MESSAGE_CONSTRAINTS);
        this.availability = d.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid Availability.
     */
    public static boolean isValidAvailability(String test) {
        return test.equalsIgnoreCase(VEHICLE_AVAILABLE_TAG) || test.equalsIgnoreCase(VEHICLE_BUSY_TAG);
    }

    /**
     * Sets availability to given availability.
     * @param updatedAvailability updated availability
     */
    public void setAvailability(Availability updatedAvailability) {
        this.availability = updatedAvailability.availability;
    }

    public String getAvailabilityTag() {
        return this.availability;
    }

    @Override
    public String toString() {
        return availability;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Availability // instanceof handles nulls
                && availability.equals(((Availability) other).availability)); // state check
    }

    @Override
    public int hashCode() {
        return availability.hashCode();
    }

}


