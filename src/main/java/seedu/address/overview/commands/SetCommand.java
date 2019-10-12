package seedu.address.overview.commands;

import seedu.address.overview.model.Model;

/**
 * Adds a transaction to the transaction list.
 */
public abstract class SetCommand extends Command {

    double amount;

    public static final String COMMAND_WORD = "set";

}
