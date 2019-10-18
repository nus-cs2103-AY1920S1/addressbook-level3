package seedu.address.model.waste.exceptions;

public class WasteMonthException extends RuntimeException {

    public WasteMonthException(String message) {
        super("Waste Month is not valid. " + message);
    }
}
