package seedu.address.financialtracker.model.expense;

import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;

/**
 * Time added for the expense
 */
public class Time {
    public static final String MESSAGE_CONSTRAINTS =
            "Time should only contain numerals and be written in the 24hrs format.\n"
                    + "Valid formats: 1947, 2359, 0000\n"
                    + "Invalid formats: 07:48, 900, 2401, etc.";

    public static final String VALIDATION_REGEX = "([01]?[0-9]|2[0-3])[0-5][0-9]";
    public final String value;
    public final int valueToCompare;
    public final String storageTime;

    public Time(String time) {
        requireNonNull(time);
        this.valueToCompare = Integer.parseInt(time);
        this.value = formatTime(time);
        this.storageTime = time;
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

    public static Time getCurrentTime() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("HHmm");//dd/MM/yyyy
        java.util.Date now = new java.util.Date();
        String value = sdfDate.format(now);
        return new Time(value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                && value.equals(((Time) other).value)); // state check
    }
}
