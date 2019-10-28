package seedu.address.model.quiz.exceptions;

/**
 * Signals that the filter type is not correct.
 */
public class FilterTypeNotFoundException extends RuntimeException {
    public FilterTypeNotFoundException() {
        super("This filter type is not in the list of acceptable filter types.");
    }

}
