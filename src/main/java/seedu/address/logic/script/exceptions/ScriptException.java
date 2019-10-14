package seedu.address.logic.script.exceptions;

/**
 * Represents an <code>Exception</code> that occurs during script evaluation.
 */
public class ScriptException extends Exception {
    /**
     * Constructs a <code>ScriptException</code> with the specified detail message and cause.
     * @param message the detail message
     * @param cause the cause
     */
    public ScriptException(String message, Throwable cause) {
        super(message, cause);
    }
}
