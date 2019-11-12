package seedu.address.model.flashcard.exceptions;

/**
 * Signals that the conversion from a ScheduleIncrement string to a ScheduleIncrement was not successful because none
 * of the cases were matched.
 */
public class StringToScheduleIncrementConversionException extends RuntimeException {

    public static final String ERROR_MESSAGE = "Exception while converting ScheduleIncrement string in JSON storage "
            + "to ScheduleIncrement!";

    public StringToScheduleIncrementConversionException() {
        super(ERROR_MESSAGE);
    }
}
