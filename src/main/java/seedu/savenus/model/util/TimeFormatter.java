package seedu.savenus.model.util;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Adapted from Riccardo Casatta for the idea/implementation for this class
 * https://stackoverflow.com/a/23215152/11925699
 */
public class TimeFormatter {
    // Convert TimeUnits to milliseconds
    public static final List<Long> TIMES = Arrays.asList(
            TimeUnit.DAYS.toMillis(365),
            TimeUnit.DAYS.toMillis(30),
            TimeUnit.DAYS.toMillis(1),
            TimeUnit.HOURS.toMillis(1),
            TimeUnit.MINUTES.toMillis(1),
            TimeUnit.SECONDS.toMillis(1));

    // Map times to its corresponding string
    public static final List<String> TIMES_STRING = Arrays.asList(
            "year", "month", "day", "hour", "minute", "second");

    /**
     * Format input duration into timeAgo format.
     * @param durationInMillis Input duration
     */
    public static String format(long durationInMillis) {
        StringBuffer res = new StringBuffer();
        // Find the largest unit of time
        for (int i = 0; i < TimeFormatter.TIMES.size(); i++) {
            Long current = TimeFormatter.TIMES.get(i);
            long temp = durationInMillis / current;
            if (temp > 0) {
                res.append(temp).append(" ").append(TimeFormatter.TIMES_STRING.get(i))
                    .append(temp != 1 ? "s" : "").append(" ago");
                break;
            }
        }
        return "".equals(res.toString())
            ? "0 seconds ago"
            : res.toString();
    }
}
