package seedu.address.overview.commands;

import seedu.address.overview.model.Model;

/**
 * Deletes a transaction to the transaction list.
 */
public abstract class NotifyCommand extends Command {

    int amount;

    public static final String COMMAND_WORD = "notify";

}
