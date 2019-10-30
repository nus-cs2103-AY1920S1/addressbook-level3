package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a particular type of command that is undoable.
 */
public abstract class UndoableCommand extends Command {

    /**
     * Returns the description of the command.
     */
    public abstract String getDescription();

    @Override
    public CommandResult run(Model model) throws CommandException {
        validate(model);
        model.commitModel(getDescription());
        return execute(model);
    }

}
