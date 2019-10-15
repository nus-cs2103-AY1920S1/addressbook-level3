package seedu.address.inventory.model.exception;

/**
 * Signals that the user input is referring to a non-existent {@code Item} object.
 */
public class NoSuchItemException extends Exception {

    private String msg;

    public NoSuchItemException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String toString() {
        return this.msg;
    }
}
