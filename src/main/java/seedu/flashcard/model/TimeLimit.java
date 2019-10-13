package seedu.flashcard.model;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * The time limit for doing a quiz
 * Guarantees: Does not exceed an hour, minutes and seconds are both between 0 and 60.
 */
public class TimeLimit {

    private static final String MESSAGE_CONSTRAINTS = "Time limit must be in the format of two integers,"
            + "representing minutes and seconds respectively. Since this a model system, we do not "
            + "support time limit more than or equals to an hour.";
    private static final String MESSAGE_AFTER_MINUTES = " minutes ";
    private static final String MESSAGE_AFTER_SECONDS = " seconds ";
    private int minutes;
    private int seconds;

    // Default Time Limit, 1 minute.
    public TimeLimit() {
        minutes = 1;
        seconds = 0;
    }

    public TimeLimit(int minutes, int seconds) {
        checkArgument(isValidTimeLimit(minutes, seconds), MESSAGE_CONSTRAINTS);
        this.minutes = minutes;
        this.seconds = seconds;
    }

    /**
     * Ensure both minutes and seconds are between 0 and 60
     * The total time should be less than an hour
     */
    private boolean isValidTimeLimit(int minutes, int seconds) {
        if (seconds < 0 || minutes < 0 || seconds >= 60 || minutes >= 60) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String minutesString = Integer.toString(minutes);
        String secondsString = Integer.toString(seconds);
        return minutesString + MESSAGE_AFTER_MINUTES + secondsString + MESSAGE_AFTER_SECONDS;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof TimeLimit)) {
            return false;
        }
        return minutes == ((TimeLimit) other).minutes && seconds == ((TimeLimit) other).seconds;
    }

}
