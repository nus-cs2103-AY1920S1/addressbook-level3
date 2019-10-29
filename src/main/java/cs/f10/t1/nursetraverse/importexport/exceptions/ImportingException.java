package cs.f10.t1.nursetraverse.importexport.exceptions;

/**
 *  Represents an error that occurs during the import process, unrelated to IO.
 */
public class ImportingException extends Exception {
    public ImportingException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code ImportingException} with the specified detail {@code message} and {@code cause}.
     */
    public ImportingException(String message, Throwable cause) {
        super(message, cause);
    }
}
