package seedu.address.model.cap.module.exceptions;

/**
 * Signals that the operation will result in duplicate Modules (Modules are considered duplicates if they have the same
 * title, module code, credit and semester).
 */
public class DuplicateModuleException extends RuntimeException {
    public DuplicateModuleException() {
        super("Operation would result in duplicate modules");
    }
}
