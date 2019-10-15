package seedu.address.model.task.exceptions;

public class RedundantOperationException extends RuntimeException {
    public RedundantOperationException(String message) {
        super(message);
    }
}
