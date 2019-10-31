package cs.f10.t1.nursetraverse.importexport.exceptions;

/**
 * Represents an error that occurs during the export process, unrelated to IO.
 */
public class ExportingException extends Exception {
    public ExportingException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code ExportException} with the specified detail {@code message} and {@code cause}.
     */
    public ExportingException(String message, Throwable cause) {
        super(message, cause);
    }
}
