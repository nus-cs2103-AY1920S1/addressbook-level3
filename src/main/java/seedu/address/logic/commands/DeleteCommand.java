package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Deletes a person, event, or training.
 */
public abstract class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = "Delete general usage";

    @Override
    public abstract CommandResult execute(Model model) throws CommandException;

    @Override
    public boolean isUndoable() {
        return true;
    }

}
