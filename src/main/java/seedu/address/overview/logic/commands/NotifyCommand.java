package seedu.address.overview.logic.commands;

import java.text.DecimalFormat;

/**
 * Deletes a transaction to the transaction list.
 */
public abstract class NotifyCommand extends Command {

    public static final String COMMAND_WORD = "notify";
    protected static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    protected long amount;

}
