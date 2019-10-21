package seedu.address.overview.logic.commands;

/**
 * Adds a transaction to the transaction list.
 */
public abstract class SetCommand extends Command {

    public static final String COMMAND_WORD = "set";

    protected double amount;
}
