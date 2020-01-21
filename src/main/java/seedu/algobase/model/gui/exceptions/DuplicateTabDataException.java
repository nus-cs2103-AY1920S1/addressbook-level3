package seedu.algobase.model.gui.exceptions;

/**
 * Signals that the operation will result in duplicate TabsData (TabsData are considered duplicates if they have the
 * same identity).
 */
public class DuplicateTabDataException extends RuntimeException {
    public DuplicateTabDataException() {
        super("Operation would result in duplicate problems");
    }
}
