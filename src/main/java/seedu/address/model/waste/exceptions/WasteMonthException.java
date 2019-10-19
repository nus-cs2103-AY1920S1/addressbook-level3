package seedu.address.model.waste.exceptions;

/**
 * Signals that the operation will result in an invalid waste month.
 */
public class WasteMonthException extends RuntimeException {

    public WasteMonthException(String message) {
        super("Waste Month is not valid. " + message);
    }
}
