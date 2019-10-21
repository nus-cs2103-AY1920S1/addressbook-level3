package seedu.jarvis.model.cca.exceptions;

public class MaxProgressNotSetException extends RuntimeException {
    public MaxProgressNotSetException() {
        super("Max progress is not yet set!");
    }
}
