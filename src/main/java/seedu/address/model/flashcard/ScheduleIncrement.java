package seedu.address.model.flashcard;

/**
 * Enum class for use for remind feature
 */
public enum ScheduleIncrement {
    FIRST,
    SECOND,
    THIRD,
    FOURTH,
    FIFTH,
    SIXTH,
    FINAL;

    private int numberOfDays;

    static {
        FIRST.numberOfDays = 1;
        SECOND.numberOfDays = 1;
        THIRD.numberOfDays = 2;
        FOURTH.numberOfDays = 3;
        FIFTH.numberOfDays = 5;
        SIXTH.numberOfDays = 5;
        FINAL.numberOfDays = 7;
    }

    /**
     * Get int value of increment
     * @return int value of increment
     */
    public int getNumberOfDays() {
        return numberOfDays;
    }
}
