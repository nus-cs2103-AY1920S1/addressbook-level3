package seedu.address.overview.logic.commands;

import java.text.DecimalFormat;

/**
 * Adds a transaction to the transaction list.
 */
public abstract class SetCommand extends Command {

    public static final String COMMAND_WORD = "set";
    protected static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    protected double amount;

    /**
     * Checks if a value is out of specified range.
     * @param amount The value to be checked.
     * @return true if it is out of range, false if not.
     */
    protected boolean outOfRange(double amount) {
        return amount > 10000000;
    }
}
