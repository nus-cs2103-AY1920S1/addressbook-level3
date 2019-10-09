package seedu.address.commons.core.time;

/**
 * Represents the duration in units of thirty minutes.
 */
public class DurationInHalfHour {
    private final int numberOfHalfHour;

    public DurationInHalfHour(int numberOfHalfHour) {
        this.numberOfHalfHour = numberOfHalfHour;
    }

    public int getNumberOfHalfHour() {
        return numberOfHalfHour;
    }
}
