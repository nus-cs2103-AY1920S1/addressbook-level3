package seedu.address.model.quiz.exceptions;

/**
 * Signals that the filter type is not correct.
 */
public class WrongDateFormatException extends RuntimeException {
    public WrongDateFormatException() {
        super("The format of the date is wrong.");
    }

}
