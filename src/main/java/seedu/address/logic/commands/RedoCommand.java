package seedu.address.logic.commands;

import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Redoes a model-changing command, effectively migrating the model to a future state.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_NO_MODEL = "There is nothing to be redone.";
    public static final String MESSAGE_SUCCESS = "Redid the undoable command";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Optional<Model> nextModel = model.migrateModel();

        if (nextModel.isEmpty()) {
            throw new CommandException(MESSAGE_NO_MODEL);
        }

        model.fillModelData(nextModel.get());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
