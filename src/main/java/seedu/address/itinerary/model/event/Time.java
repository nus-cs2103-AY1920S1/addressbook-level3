package seedu.address.itinerary.model.event;

/**
 * Time attribute for the event in the itinerary.
 */
public class Time {
    public static final String MESSAGE_CONSTRAINTS =
            "Time should only contain numerals and be written in the 24hrs format.\n"
            + "Valid formats: 0000, 0900, 1700, 2359 ✓\n"
            + "Invalid formats: 00:00, 900, 9999 ✗";

    public static final String VALIDATION_REGEX = "([01][0-9]|2[0-3])[0-5][0-9]";
    public final String time;
    public final String oldTime;

    public Time(String time) {
        assert time != null;
        oldTime = time;
        this.time = formatTime(time);
    }

    public String getOriginalTime() {
        return oldTime;
    }

    /**
     * Returns true if a given string is a valid time.
     */
    public static boolean isValidTime(String time) {
        return time.matches(VALIDATION_REGEX);
    }

    /**
     * @param time current time.
     * @return format current time based on HH:MM a.m. / p.m..
     */
    private String formatTime(String time) {
        int hour = Integer.parseInt(time.substring(0, 2));
        String minutes = time.substring(2);
        String zone;
        if (hour > 12) {
            hour -= 12;
            zone = "p.m.";
        } else if (hour == 0) {
            hour = 12;
            zone = "a.m.";
        } else {
            zone = "a.m.";
        }

        return hour + ":" + minutes + " " + zone;
    }

    @Override
    public String toString() {
        return time;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                && time.equals(((Time) other).time)); // state check
    }
}
