package seedu.address.transaction.commands;

import seedu.address.person.logic.commands.exceptions.CommandException;
import seedu.address.transaction.logic.exception.ParseException;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.exception.NoSuchIndexException;
import seedu.address.transaction.model.exception.NoSuchPersonException;

/**
 * Backs out from a find command interface to the complete transaction list.
 */
public class BackCommand extends Command {
    public static final String COMMAND_WORD = "back";

    @Override
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel)
            throws NoSuchIndexException, CommandException, NoSuchPersonException, ParseException {
        return new CommandResult("");
    }
}
