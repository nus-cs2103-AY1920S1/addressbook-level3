package seedu.address.inventory.storage.exception;

/**
 * Signals that there is an error in the information in the file.
 */
public class ParseFileException extends Exception {
    private String msg;

    public ParseFileException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String toString() {
        return this.msg;
    }
}
