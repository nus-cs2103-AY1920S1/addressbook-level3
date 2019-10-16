package seedu.address.itinerary.model.Event;

public class Time {
    public static final String MESSAGE_CONSTRAINTS =
            "Time should only contain numbers and be written in the 24hrs format. Example: 2359.";

    public static final String VALIDATION_REGEX = ".*";
    public final String time;

    public Time(String time) {
        this.time = time;
    }

    /**
     * Returns true if a given string is a valid time.
     */
    public static boolean isValidTime(String time) {
        return time.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return time;
    }
}
