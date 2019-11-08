package seedu.guilttrip.model.entry;

import static seedu.guilttrip.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the guilttrip book. Guarantees: immutable; is
 * valid as declared in {@link #isValidPeriod(long, char)}
 */
public class Period {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the guilttrip must not be a whitespace, otherwise " " (a
     * blank string) becomes a valid input.
     */
    // public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final long duration;
    public final char interval; //days (d)/ months (m)/ years (y)

    /**
     * Constructs a {@code period}.
     *
     * @param duration A valid duration.
     */
    public Period(long duration, char interval) {
        checkArgument(isValidPeriod(duration, interval), MESSAGE_CONSTRAINTS);
        this.duration = duration;
        this.interval = interval;
    }

    /**
     * Constructs a {@code period}.
     *
     * @param period Period in String format.
     */
    public Period(String period) {
        duration = Long.parseLong(period.substring(0, period.length() - 1));
        interval = period.charAt(period.length() - 1);
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidPeriod(long testDuration, char testInterval) {
        // return test.matches(VALIDATION_REGEX); // TODO
        return true;
    }

    public long getDuration() {
        return duration;
    }

    public char getInterval() {
        return interval;
    }

    @Override
    public String toString() {
        return String.format("%d%c", duration, interval);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Period // instanceof handles nulls
                && duration == ((Period) other).duration // state check
                && interval == ((Period) other).interval); // state check
    }

}
