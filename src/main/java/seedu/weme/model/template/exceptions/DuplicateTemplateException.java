package seedu.weme.model.template.exceptions;

/**
 * Signals that the operation will result in duplicate Templates (Templates are considered duplicates if they have the
 * same identity).
 */
public class DuplicateTemplateException extends RuntimeException {
    public DuplicateTemplateException() {
        super("Operation would result in duplicate templates");
    }
}

