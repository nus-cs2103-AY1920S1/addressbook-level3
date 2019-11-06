package seedu.address.ui.util;

import java.time.LocalTime;

/**
 * Utility time class that contains methods to format or manipulate time objects.
 */
public class TimeUtil {
    public static int formatTimeToInt(LocalTime localTime) {
        return localTime.getHour() * 100 + (localTime.getMinute() / 2) * 2;
    }

    /**
     * Gets the time difference in minutes given a start and end time.
     * @param start
     * @param end
     * @return int duration between the start and end time.
     */
    public static int getTimeDifference(LocalTime start, LocalTime end) {
        int startTime = formatTimeToInt(start);
        int endTime = formatTimeToInt(end);
        int startTimeHours = startTime / 100;
        int endTimeHours = endTime / 100;
        int hours = endTimeHours - startTimeHours;
        int startTimeMinutes = startTime % 100;
        int endTimeMinutes = endTime % 100;
        int minutes = endTimeMinutes - startTimeMinutes;
        return hours * 60 + minutes;
    }

    /**
     * Method to convert int hours to time in 24hours clock representation.
     * @param time Time in hours to be formatted.
     * @return String that represents the time.
     */
    public static String formatIntToTime(int time) {
        if (time == 0) {
            return "0000";
        } else if (time < 10) {
            return "0" + (time * 100);
        } else {
            return time * 100 + "";
        }
    }
}
