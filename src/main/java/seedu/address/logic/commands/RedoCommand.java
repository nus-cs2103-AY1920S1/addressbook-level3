package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.history.HistoryManager;

/**
 * Redo the previous undone Command.
 */
public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo Command Success";
    public static final String MESSAGE_FAILURE_EMPTY_STACK = "Redo Command Failure: No available "
        + "commands to be redone.";
    public static final String MESSAGE_FAILURE = "Redo Command Failure:"
        + " Redo command can only be executed after a undo Command.";
    @Override
    public CommandResult execute(Model model) throws CommandException {
        HistoryManager history = new HistoryManager();
        if (history.isRedoneEmpty()) {
            return new CommandResult(MESSAGE_FAILURE_EMPTY_STACK);
        } else if (!(history.getLatestCommand() instanceof UndoCommand)) {
            return new CommandResult(MESSAGE_FAILURE);
        } else {
            Command redoneCommand = model.redo();
            if (redoneCommand instanceof TrainingCommand) {
                return new CommandResult(MESSAGE_SUCCESS, ((TrainingCommand) redoneCommand).getDate(), model);
            } else {
                return new CommandResult(MESSAGE_SUCCESS);
            }
        }
    }
    @Override
    public boolean isUndoable() {
        return false;
    }
}
