package seedu.address.overview.commands;


/**
 * Deletes a transaction to the transaction list.
 */
public abstract class NotifyCommand extends Command {

    public static final String COMMAND_WORD = "notify";

    protected int amount;

}
