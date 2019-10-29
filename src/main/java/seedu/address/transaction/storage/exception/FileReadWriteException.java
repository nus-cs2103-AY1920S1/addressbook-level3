package seedu.address.transaction.storage.exception;

/**
 * Signals that the file cannot be read or written to.
 * */
public class FileReadWriteException extends Exception {
    private String msg;
    public FileReadWriteException(String msg) {
        super(msg);
        this.msg = msg;
    }

    @Override
    public String toString() {
        return this.msg;
    }
}
