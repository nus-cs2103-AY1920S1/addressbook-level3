package seedu.address.inventory.model.exception;

/**
 * Signals that the user input is referring to an index beyond the maximum index,
 * thus referring to a non-existent {@code Item} object.
 */
public class NoSuchIndexException extends Exception {

    private String msg;

    public NoSuchIndexException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String toString() {
        return this.msg;
    }
}
