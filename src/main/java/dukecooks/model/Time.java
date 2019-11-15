package dukecooks.model;

import static dukecooks.commons.util.AppUtil.checkArgument;
import static dukecooks.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 *  Represents the a valid time in HH:mm format.
 */
public class Time {

    public static final String MESSAGE_HOUR_CONSTRAINTS =
            "Hour should be numeric and within valid range of 0 - 23!"
            + "It is a required field.";

    public static final String MESSAGE_MINUTE_CONSTRAINTS =
            "Minute should be numeric and within valid range of 0 - 59."
            + "It is a required field.";

    public static final String MESSAGE_TIME_CONSTRAINTS =
            "Time entered is invalid!"
            + "It should be expressed in HH:MM format";

    public static final String TIME_VALIDATION_REGEX = "(\\d{2})[\\:](\\d{2})";

    private final int hour;
    private final int minute;

    /**
     * Constructs a {@code Time}.
     * @param hour A valid hour.
     * @param minute A valid minute.
     */
    public Time(int hour, int minute) {
        requireAllNonNull(hour, minute);
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Checks if it is a valid time.
     */
    public static boolean isValidTime(String time) {
        if (time == null || !isValidTimeFormat(time)) {
            return false;
        }

        String[] t = time.split("[\\:]");
        int hour = Integer.parseInt(t[0]);
        int minute = Integer.parseInt(t[1]);

        return isValidHour(hour) && isValidMinute(minute);
    }

    /**
     * Constructs a {@code Time} from String.
     * This method should only take valid time format inputs.
     * @param time A valid time in String format.
     */
    public static Time generateTime(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time));
        String[] t = time.split("[\\:]");
        return new Time(Integer.parseInt(t[0]), Integer.parseInt(t[1]));
    }

    /**
     * Checks if time is in the correct specified time format.
     */
    public static boolean isValidTimeFormat(String time) {
        return time.matches(TIME_VALIDATION_REGEX);
    }

    /**
     * Checks if it is valid hour.
     */
    public static boolean isValidHour(int hour) {
        return hour >= 0 && hour < 24;
    }

    /**
     * Returns true if minute is within valid range.
     */
    public static boolean isValidMinute(int minute) {
        return minute >= 0 && minute < 60;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null) {
            return false;
        }
        Time otherTime = (Time) other;
        return (otherTime.getHour() == getHour())
                && (otherTime.getMinute() == getMinute());
    }

    @Override
    public int hashCode() {
        return Objects.hash(hour, minute);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%02d", getHour()))
                .append(":")
                .append(String.format("%02d", getMinute()));
        return builder.toString();
    }


}
