package seedu.ezwatchlist.model.show;

import static java.util.Objects.requireNonNull;
import static seedu.ezwatchlist.commons.util.AppUtil.checkArgument;

/**
 * Represents a Show's Running Time in the watchlist.
 * Guarantees: immutable.
 */
public class RunningTime {
    // For now the running time will be an integer, since that is what the API returns it as.
    public static final String MESSAGE_CONSTRAINTS =
            "Running time can take any integer values, and it should not be blank";
    public static final String MESSAGE_CONSTRAINTS2 =
            "Running time can take only positive values.";

    public final int value;

    public RunningTime() {
        value = 0;
    }

    /**
     * Constructs an {@code RunningTime}.
     *
     * @param runningTime A valid running time.
     */
    public RunningTime(int runningTime) {
        requireNonNull(runningTime);
        checkArgument(isValidRunningTime(runningTime), MESSAGE_CONSTRAINTS2);
        value = runningTime;
    }

    /**
     * Returns true if a given integer is a valid running time.
     */
    public static boolean isValidRunningTime(int test) {
        return test > 0;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RunningTime // instanceof handles nulls
                && value == ((RunningTime) other).value); // state check
    }

    @Override
    public int hashCode() {
        return value;
    }
}
