package seedu.address.model.flashcard;

import seedu.address.model.flashcard.exceptions.StringToScheduleIncrementConversionException;

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
    private ScheduleIncrement nextIncrement;

    static {
        FIRST.numberOfDays = 1;
        SECOND.numberOfDays = 1;
        THIRD.numberOfDays = 2;
        FOURTH.numberOfDays = 3;
        FIFTH.numberOfDays = 5;
        SIXTH.numberOfDays = 5;
        FINAL.numberOfDays = 7;

        FIRST.nextIncrement = SECOND;
        SECOND.nextIncrement = THIRD;
        THIRD.nextIncrement = FOURTH;
        FOURTH.nextIncrement = FIFTH;
        FIFTH.nextIncrement = SIXTH;
        SIXTH.nextIncrement = FINAL;
        FINAL.nextIncrement = FINAL;
    }

    public static ScheduleIncrement getScheduleIncrementFromString(String stringScheduleIncrement)
            throws StringToScheduleIncrementConversionException {
        switch (stringScheduleIncrement) {
        case "FIRST":
            return FIRST;
        case "SECOND":
            return SECOND;
        case "THIRD":
            return THIRD;
        case "FOURTH":
            return FOURTH;
        case "FIFTH":
            return FIFTH;
        case "SIXTH":
            return SIXTH;
        case "FINAL":
            return FINAL;
        default:
            throw new StringToScheduleIncrementConversionException();
        }
    }

    /**
     * Get int value of increment.
     * @return int value of increment
     */
    public int getNumberOfDays() {
        return numberOfDays;
    }

    /**
     * Get ScheduleIncrement value of next increment.
     * @return ScheduleIncrement value of next increment
     */
    public ScheduleIncrement getNextIncrement() {
        return nextIncrement;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
