package seedu.savenus.model.purchase;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Factory class to create TimeOfPurchase.
 */
public class TimeOfPurchase {

    public static final String MESSAGE_CONSTRAINTS =
            "TimeOfPurchase should be a long, and in milliseconds since epoch";

    private String timeOfPurchaseInMillisSinceEpochString;

    public TimeOfPurchase(String timeOfPurchaseInMillisSinceEpochString) {
        this.timeOfPurchaseInMillisSinceEpochString = timeOfPurchaseInMillisSinceEpochString;
    }

    public long getTimeOfPurchaseInMillisSinceEpoch() {
        return Long.parseLong(timeOfPurchaseInMillisSinceEpochString);
    }

    /**
     * Check whether test string is a valid {@code TimeOfPurchase}.
     * @param testDateTimeInMillisSinceEpoch
     */
    public static boolean isValidTimeOfPurchase(String testDateTimeInMillisSinceEpoch) {
        try {
            LocalDateTime.ofInstant(Instant.ofEpochMilli(
                    Long.parseLong(testDateTimeInMillisSinceEpoch)), ZoneId.systemDefault());;
        } catch (NumberFormatException e) {
            return false;
        } catch (DateTimeException e) {
            return false;
        }
        return true;
    }

    /**
     * Generate a {$code TimeOfPurchase} instance based on the current time.
     * @return
     */
    public static TimeOfPurchase generate() {
        return new TimeOfPurchase(
                Long.toString(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
    }
}
