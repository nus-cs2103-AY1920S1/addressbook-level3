//@@author nattanyz
package dream.fcard.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.function.Predicate;

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
    public static String getStringFromDuration(Duration duration) {
        Predicate<String> equalsZero = (s -> s.equals("0"));

        StringBuilder sb = new StringBuilder();
        String hours = Long.toString(duration.toHoursPart());
        String minutes = Long.toString(duration.toMinutesPart());
        String seconds = Long.toString(duration.toSecondsPart());

        if (!equalsZero.test(hours)) {
            sb.append(hours).append(" hours ");
        }

        if (!equalsZero.test(minutes)) {
            sb.append(minutes).append(" minutes ");
        }

        if (!equalsZero.test(seconds)) {
            sb.append(seconds).append(" seconds");
        }

        return sb.toString();
    }

    /**
     * Returns the String representation of the given LocalDateTime object.
     * Format: "M/D/Y, HH:MM AM/PM", similar to "23/8/16, 1:12 PM".
     * @param localDateTime The LocalDateTime object to be represented as a String.
     * @return The String representation of the given LocalDateTime object.
     */
    public static String getStringFromDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
        return localDateTime.format(formatter);
    }

    // todo: generate cut-off date for "past week", "past month" etc to pass to Stats class
}
