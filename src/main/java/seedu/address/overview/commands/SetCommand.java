package seedu.address.overview.commands;

import seedu.address.overview.model.Model;
import seedu.address.overview.model.Transaction;
import seedu.address.overview.ui.TransactionMessages;

/**
 * Adds a transaction to the transaction list.
 */
public abstract class SetCommand extends Command {
    public static final String COMMAND_WORD = "add";
}
