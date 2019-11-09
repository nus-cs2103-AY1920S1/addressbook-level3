package seedu.savenus.model.purchase;

import seedu.savenus.model.util.TimeStamp;

/**
 * Class to create TimeOfPurchase.
 */
public class TimeOfPurchase extends TimeStamp implements Comparable<TimeOfPurchase> {

    public static final String MESSAGE_CONSTRAINTS =
            "TimeOfPurchase should be a long, and in milliseconds since epoch";

    public TimeOfPurchase(String timeStamp) {
        super(timeStamp);
    }

    /**
     * Generate a {$code TimeOfPurchase} instance based on the current time.
     * @return the time of purchase of the food item.
     */
    public static TimeOfPurchase generate() {
        return new TimeOfPurchase(TimeStamp.generateCurrentTimeStamp());
    }

    @Override
    public int compareTo(TimeOfPurchase other) {
        return getTimeStampInLocalDateTime()
                .compareTo(other.getTimeStampInLocalDateTime());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TimeOfPurchase)) {
            return false;
        }

        TimeOfPurchase otherTimeOfPurchase = (TimeOfPurchase) other;
        return otherTimeOfPurchase.getTimeStampInMillisSinceEpoch() == this.getTimeStampInMillisSinceEpoch();
    }
}
