package seedu.moolah.logic.commands;

import seedu.moolah.logic.commands.exceptions.CommandException;
import seedu.moolah.model.Model;

/**
 * Undoes the previous model-changing command, effectively reverting the model to its previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_NO_MODEL = "There is nothing to be undone.";
    public static final String MESSAGE_SUCCESS = "Undid \"%1$s\".";

    @Override
    protected void validate(Model model) throws CommandException {
        if (!model.canRollback()) {
            throw new CommandException(MESSAGE_NO_MODEL);
        }
    }

    @Override
    public CommandResult execute(Model model) {
        String prevCommandDesc = model.getLastCommandDesc();
        model.rollbackModel();
        return new CommandResult(String.format(MESSAGE_SUCCESS, prevCommandDesc));
    }
}
