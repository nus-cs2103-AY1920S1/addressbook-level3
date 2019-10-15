package seedu.address.transaction.commands;

import seedu.address.person.logic.commands.exceptions.CommandException;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.exception.NoSuchIndexException;
import seedu.address.transaction.model.exception.NoSuchPersonException;

/**
 * Deletes a transaction to the transaction list.
 */
public abstract class DeleteCommand extends Command {
    public static final String COMMAND_WORD = "delete";

    @Override
    public abstract CommandResult execute(Model model, seedu.address.person.model.Model personModel)
            throws NoSuchIndexException, CommandException, NoSuchPersonException;
}
