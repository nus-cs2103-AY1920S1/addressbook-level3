package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Adds a expense to the address book.
 */
public abstract class AddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add";

    @Override
    protected abstract void validate(Model model) throws CommandException;

    @Override
    protected abstract CommandResult execute(Model model);
}
