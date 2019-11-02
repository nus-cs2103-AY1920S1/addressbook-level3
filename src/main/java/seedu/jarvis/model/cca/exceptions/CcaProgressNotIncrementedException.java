package seedu.jarvis.model.cca.exceptions;

public class CcaProgressNotIncrementedException extends Throwable {
    public CcaProgressNotIncrementedException() {
        super("Cca progress is not yet incremented!");
    }
}
