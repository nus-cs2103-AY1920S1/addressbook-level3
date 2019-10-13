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

    private long timeOfPurchaseInMillisSinceEpoch;

    public TimeOfPurchase(long timeOfPurchaseInMillisSinceEpoch) {
        this.timeOfPurchaseInMillisSinceEpoch = timeOfPurchaseInMillisSinceEpoch;
    }

    public long getTimeOfPurchaseInMillisSinceEpoch() {
        return timeOfPurchaseInMillisSinceEpoch;
    }

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

    public static TimeOfPurchase generate() {
        return new TimeOfPurchase(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }
}
