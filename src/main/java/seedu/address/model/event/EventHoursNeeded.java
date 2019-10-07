package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

/**
 * Represent the total duration, in hours, of the Event.
 */
public class EventHoursNeeded {
    public static final String MESSAGE_CONSTRAINTS =
            "Hours Needed should be an integer, and < 1000";
    public final int value;

    /**
     * Constructs a {@code EventHoursNeeded}.
     *
     * @param hours A valid number of hours representing the duration of the event.
     */
    public EventHoursNeeded(String hours) {
        requireNonNull(hours);
        //checkArgument(isValidPhone(hours), MESSAGE_CONSTRAINTS);
        value = Integer.parseInt(hours);
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidEventHours(String test) {
        try {
            return Integer.parseInt(test) < 1000;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value + "";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventHoursNeeded// instanceof handles nulls
                && value == ((EventHoursNeeded) other).value); // state check
    }
}
