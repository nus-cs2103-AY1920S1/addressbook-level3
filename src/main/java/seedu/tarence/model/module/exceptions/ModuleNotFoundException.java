package seedu.tarence.model.module.exceptions;

/**
 * Signals that the operation is unable to find the specified module.
 */
public class ModuleNotFoundException extends RuntimeException {
    public ModuleNotFoundException() {
        super ("Operation is unable to find the specified module.");
    }
}
