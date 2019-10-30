package seedu.savenus.model.util;

import static java.util.Objects.requireNonNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Abstract class to create a TimeStamp to register the time of purchase/time of saving.
 */
public abstract class TimeStamp {
    private String timeStampString;

    public TimeStamp(String timeStampString) {
        requireNonNull(timeStampString);
        this.timeStampString = timeStampString;
    }

    public long getTimeStampInMillisSinceEpoch() {
        return Long.parseLong(timeStampString);
    }

    public LocalDateTime getTimeStampInLocalDateTime() {
        return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(getTimeStampInMillisSinceEpoch()), ZoneId.systemDefault());
    }
    /**
     * Check whether test string is a valid {@code TimeStamp}.
     * @param testDateTimeInMillisSinceEpoch
     */
    public static boolean isValidTimeStamp(String testDateTimeInMillisSinceEpoch) {
        requireNonNull(testDateTimeInMillisSinceEpoch);
        try {
            LocalDateTime.ofInstant(Instant.ofEpochMilli(
                    Long.parseLong(testDateTimeInMillisSinceEpoch)), ZoneId.systemDefault());;
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Generate a String based on the current time.
     * @return String reflecting the current time in the user's timezone.
     */
    public static String generateCurrentTimeStamp() {
        return Long.toString(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }

}
