package seedu.address.model.exceptions;

public class NotInServeModeException extends RuntimeException {
    public NotInServeModeException() {
        super("Not in Serve mode!");
    }
}
