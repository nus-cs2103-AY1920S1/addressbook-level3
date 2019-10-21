package seedu.jarvis.model.cca.exceptions;

public class CcaProgressAtMaxException extends RuntimeException {
    public CcaProgressAtMaxException() {
        super("Cca progress already at maximum!");
    }
}
