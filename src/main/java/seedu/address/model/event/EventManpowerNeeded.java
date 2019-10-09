package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;


/**
 * Represents the number of manpower needed for an Event.
 */
public class EventManpowerNeeded {
    public static final String MESSAGE_CONSTRAINTS =
            "Manpower Needed should be an integer, and < 1000";
    public final int value;

    /**
     * Constructs a {@code EventManpowerNeeded}.
     *
     * @param manpowerNum A valid number of manpower needed for an Event.
     */
    public EventManpowerNeeded(String manpowerNum) {
        requireNonNull(manpowerNum);
        checkArgument(isValidEventManpowerNeeded(manpowerNum), MESSAGE_CONSTRAINTS);
        value = Integer.parseInt(manpowerNum);
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidEventManpowerNeeded(String test) {
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
                || (other instanceof EventManpowerNeeded// instanceof handles nulls
                && value == ((EventManpowerNeeded) other).value); // state check
    }
}
