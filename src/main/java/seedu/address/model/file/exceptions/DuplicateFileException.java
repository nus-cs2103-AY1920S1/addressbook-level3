package seedu.address.model.file.exceptions;

/**
 * Signals that the operation will result in duplicate Files (Files are considered duplicates if they have the same
 * file name and path).
 */
public class DuplicateFileException extends RuntimeException {
    public DuplicateFileException() {
        super("Operation would result in duplicate files");
    }
}
