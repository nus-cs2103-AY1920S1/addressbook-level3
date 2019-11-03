package seedu.address.overview.logic.commands;

/**
 * Adds a transaction to the transaction list.
 */
public abstract class SetCommand extends Command {

    public static final String COMMAND_WORD = "set";

    protected double amount;

    protected boolean outOfRange(double amount) {
        if ((amount < -10000000) || (amount > 10000000)) {
            return true;
        } else {
            return false;
        }
    }
}
