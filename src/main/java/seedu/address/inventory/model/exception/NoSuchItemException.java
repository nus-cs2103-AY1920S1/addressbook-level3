package seedu.address.inventory.model.exception;

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