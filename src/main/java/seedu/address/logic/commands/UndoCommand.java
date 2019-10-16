package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Undoes the previous model-changing command, effectively reverting the model to its previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_NO_MODEL = "There is nothing to be undone.";
    public static final String MESSAGE_SUCCESS = "Undid the last undoable command. ";

    @Override
    protected void validate(Model model) throws CommandException {
        if (!model.canRollback()) {
            throw new CommandException(MESSAGE_NO_MODEL);
        }
    }

    @Override
    public CommandResult execute(Model model) {
        // prevModel guaranteed to be present due to previous validation.
        Model prevModel = model.rollbackModel().get();
        model.resetData(prevModel);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
