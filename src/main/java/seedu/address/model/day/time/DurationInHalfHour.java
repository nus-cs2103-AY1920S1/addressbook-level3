package seedu.address.model.day.time;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents the duration with each unit representing 30 minutes.
 */
public class DurationInHalfHour {
    public static final String VALIDATION_REGEX = "^[1-9][0-9]*0$";
    public static final String MESSAGE_CONSTRAINTS = "Duration should be in interval of 30(for example: 90).";
    public static final String MESSAGE_INVALID = "Duration should be at least be divisible by 10";
    private final int numberOfHalfHour;

    public DurationInHalfHour(int numberOfHalfHour) {
        this.numberOfHalfHour = numberOfHalfHour;
    }

    public int getNumberOfHalfHour() {
        return numberOfHalfHour;
    }

    /**
     * Checks if the duration is in the correct intervals
     *
     * @throws ParseException
     */
    public static boolean isValidDuration(String test) throws ParseException {
        int duration = Integer.parseInt(test);
        if (!test.matches(VALIDATION_REGEX)) {
            throw new ParseException(MESSAGE_INVALID);
        }
        if (duration % 30 != 0) {
            throw new ParseException(MESSAGE_CONSTRAINTS);
        }
        return true;
    }
}

