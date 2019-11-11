package seedu.moolah.logic.commands;

import seedu.moolah.logic.commands.exceptions.CommandException;
import seedu.moolah.model.Model;

/**
 * Redoes a model-changing command, effectively migrating the model to a future state.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_NO_CHANGES = "There is nothing to be redone.";
    public static final String MESSAGE_SUCCESS = "Redid \"%1$s\".";

    @Override
    protected void validate(Model model) throws CommandException {
        if (!model.canMigrate()) {
            throw new CommandException(MESSAGE_NO_CHANGES);
        }
    }

    @Override
    protected CommandResult execute(Model model) {
        // changeMessage is guaranteed to present due to the validation above.
        String changeMessage = model.migrate().get();
        return new CommandResult(String.format(MESSAGE_SUCCESS, changeMessage));
    }
}
