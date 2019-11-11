package seedu.address.transaction.logic.commands;

import seedu.address.person.logic.commands.exceptions.CommandException;
import seedu.address.person.model.CheckAndGetPersonByNameModel;
import seedu.address.transaction.logic.parser.exception.ParseException;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.exception.NoSuchIndexException;
import seedu.address.transaction.model.exception.NoSuchPersonException;
import seedu.address.util.CommandResult;

/**
 * Deletes a transaction to the transaction list.
 */
public abstract class DeleteCommand extends Command {
    public static final String COMMAND_WORD = "delete";

    @Override
    public abstract CommandResult execute(Model model, CheckAndGetPersonByNameModel personModel)
            throws NoSuchIndexException, CommandException, NoSuchPersonException, ParseException;

    @Override
    public String toString() {
        return COMMAND_WORD;
    }
}
