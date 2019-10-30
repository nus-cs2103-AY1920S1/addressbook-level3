package seedu.address.inventory.logic.commands.exception;

/**
 * Signals that an error has occurred when inputting a command.
 */
public class CommandException extends Exception {
    private String msg;

    public CommandException(String msg) {
        super(msg);
        this.msg = msg;
    }

    @Override
    public String toString() {
        return this.msg;
    }
}
