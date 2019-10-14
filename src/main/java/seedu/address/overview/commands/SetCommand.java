package seedu.address.overview.commands;

/**
 * Adds a transaction to the transaction list.
 */
public abstract class SetCommand extends Command {

    public static final String COMMAND_WORD = "set";

    protected double amount;
}
