package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.history.HistoryManager;
import seedu.address.ui.feature.Feature;

/**
 * Redo the previous undone Command.
 */
public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo ";
    public static final String MESSAGE_FAILURE_EMPTY_STACK = "Redo Command Failure: No available "
        + "commands to redo.";
    public static final String MESSAGE_FAILURE = "Redo Command Failure:"
        + " Redo command can only be executed after a undo Command.";
    @Override
    public CommandResult execute(Model model) throws CommandException {
        HistoryManager history = model.getHistory();
        if (history.isRedoneEmpty()) {
            return new CommandResult(MESSAGE_FAILURE_EMPTY_STACK);
        } else if (!(history.getLatestCommand() instanceof UndoCommand)) {
            return new CommandResult(MESSAGE_FAILURE);
        } else {
            Command redoneCommand = model.redo();
            if (redoneCommand instanceof TrainingCommand) {
                return new CommandResult(MESSAGE_SUCCESS + redoneCommand
                    + " Success!", new Feature("calendar"),
                        ((TrainingCommand) redoneCommand).getDate(), model);
            } else if (redoneCommand instanceof DeleteTrainingCommand) {
                return new CommandResult(MESSAGE_SUCCESS + redoneCommand
                    + " Success!", new Feature("calendar"),
                        ((DeleteTrainingCommand) redoneCommand).getDate(), model);
            } else if (redoneCommand instanceof PerformanceCommand) {
                return new CommandResult(MESSAGE_SUCCESS + redoneCommand
                    + " Success!", new Feature("calendar"),
                        ((PerformanceCommand) redoneCommand).getDate(), model);
            } else if (redoneCommand instanceof ClearCommand) {
                return new CommandResult(MESSAGE_SUCCESS + redoneCommand
                    + " Success!", new Feature("calendar"), model);
            } else {
                return new CommandResult(MESSAGE_SUCCESS + redoneCommand
                    + " Success!");
            }
        }
    }
    @Override
    public boolean isUndoable() {
        return false;
    }
    @Override
    public String toString() {
        return "Redo Command";
    }
}
