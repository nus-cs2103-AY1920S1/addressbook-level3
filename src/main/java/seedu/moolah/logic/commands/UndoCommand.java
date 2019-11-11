package seedu.moolah.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.moolah.logic.commands.exceptions.CommandException;
import seedu.moolah.model.Model;

/**
 * Undoes the previous model-changing command, effectively reverting the model to its previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_NO_CHANGES = "There is nothing to be undone.";
    public static final String MESSAGE_SUCCESS = "Undid \"%1$s\".";

    @Override
    protected void validate(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.canRollback()) {
            throw new CommandException(MESSAGE_NO_CHANGES);
        }
    }

    @Override
    protected CommandResult execute(Model model) {
        requireNonNull(model);
        // changeMessage is guaranteed to present due to the validation above.
        String changeMessage = model.rollback().get();
        return new CommandResult(String.format(MESSAGE_SUCCESS, changeMessage));
    }
}
