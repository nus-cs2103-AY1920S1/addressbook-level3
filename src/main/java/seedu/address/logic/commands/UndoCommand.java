package seedu.address.logic.commands;

import java.util.Optional;

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
    public CommandResult execute(Model model) throws CommandException {
        Optional<Model> prevModel = model.rollbackModel();

        if (prevModel.isEmpty()) {
            throw new CommandException(MESSAGE_NO_MODEL);
        }

        model.fillModelData(prevModel.get());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
