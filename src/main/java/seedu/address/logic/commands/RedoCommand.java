package seedu.address.logic.commands;

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
    protected void validate(Model model) throws CommandException {
        if (!model.canMigrate()) {
            throw new CommandException(MESSAGE_NO_MODEL);
        }
    }

    @Override
    public CommandResult execute(Model model) {
        // nextModel is guaranteed to be present due to previous validation.
        Model nextModel = model.migrateModel().get();
        model.resetData(nextModel);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
