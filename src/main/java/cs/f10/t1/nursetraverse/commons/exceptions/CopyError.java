package cs.f10.t1.nursetraverse.commons.exceptions;

/**
 * Represents an error during the copying of data. This is a fatal error and cannot be caught with try-catch.
 */
public class CopyError extends Error {
    public CopyError(String message) {
        super(message);
    }

    public CopyError(Throwable cause) {
        super(cause);
    }

    public CopyError(String message, Throwable cause) {
        super(message, cause);
    }
}
