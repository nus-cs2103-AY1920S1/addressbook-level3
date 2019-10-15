package seedu.address.model.cheatsheet.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateCheatSheetException extends RuntimeException {
    public DuplicateCheatSheetException() {
        super("Operation would result in duplicate cheatsheets");
    }
}
