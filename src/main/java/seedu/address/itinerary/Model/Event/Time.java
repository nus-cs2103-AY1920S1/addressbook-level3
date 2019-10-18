package seedu.address.itinerary.model.Event;

public class Time {
    public static final String MESSAGE_CONSTRAINTS =
            "Time should only contain numerals and be written in the 24hrs format.\n"
            + "Valid formats: 0000, 0900, 1700, 2359 ✓\n"
            + "Invalid formats: 00:00, 900, 9999 ✗";

    public static final String VALIDATION_REGEX = "([01]?[0-9]|2[0-3])[0-5][0-9]";
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
