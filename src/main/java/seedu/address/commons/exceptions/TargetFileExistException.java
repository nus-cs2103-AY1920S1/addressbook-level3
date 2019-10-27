package seedu.address.commons.exceptions;

/**
 * Signals that the target file already exists during encryption.
 */
public class TargetFileExistException extends Exception {
    public TargetFileExistException(String targetFileName) {
        super(targetFileName);
    }
}
