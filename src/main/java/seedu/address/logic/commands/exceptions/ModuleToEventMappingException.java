package seedu.address.logic.commands.exceptions;

/**
 * Signals that there is a problem in attempting to map a module to an event.
 */
public class ModuleToEventMappingException extends RuntimeException {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public ModuleToEventMappingException(String message) {
        super(message);
    }

    /**
     * @param message should contain relevant information on the failed constraint(s)
     * @param cause of the main exception
     */
    public ModuleToEventMappingException(String message, Throwable cause) {
        super(message, cause);
    }
}
