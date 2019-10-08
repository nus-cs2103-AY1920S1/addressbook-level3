package seedu.address.inventory.model.exception;

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
