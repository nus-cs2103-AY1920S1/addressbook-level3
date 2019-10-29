//@@author nattanyz
package dream.fcard.util;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Utilities related to LocalDateTime objects.
 */
public class DateTimeUtil {

    /**
     * Calculate the duration between the given start and end times.
     * @param start The start time, in LocalDateTime form.
     * @param end The end time, in LocalDateTime form.
     * @return The duration between the start and end times, as a Duration object.
     */
    public static Duration calculateDuration(LocalDateTime start, LocalDateTime end) {
        return Duration.between(start, end);
    }

    /**
     * Returns the String representation of the given Duration object.
     * Format: "X hours, Y minutes, Z seconds"
     * @param duration The duration to be represented as a String.
     * @return The String representation of the given Duration object.
     */
    public static String durationToString(Duration duration) {
        StringBuilder sb = new StringBuilder();
        sb.append(duration.toHoursPart());
        sb.append(" hours, ");
        sb.append(duration.toMinutesPart());
        sb.append(" minutes, ");
        sb.append(duration.toSecondsPart());
        sb.append(" seconds");
        return sb.toString();
    }

    // todo: generate cut-off date for "past week", "past month" etc to pass to Stats class
}
