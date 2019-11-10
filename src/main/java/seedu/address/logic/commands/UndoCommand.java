package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.history.HistoryManager;
import seedu.address.ui.feature.Feature;

/**
 * Undo the most recent undoable Command.
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo ";
    public static final String MESSAGE_FAILURE = "Undo Command Failure:"
        + " No available commands to be undone. "
        + " Commands that can be undone is as follows: add, delete, edit, clear and training.";
    @Override
    public CommandResult execute(Model model) throws CommandException {
        HistoryManager history = model.getHistory();
        if (history.isUndoneEmpty()) {
            return new CommandResult(MESSAGE_FAILURE);
        }
        while (!history.getLatestCommand().isUndoable()) {
            if (history.isUndoneEmpty()) {
                return new CommandResult(MESSAGE_FAILURE);
            } else {
                history.getCommands().pop();
                history.getAddressBooks().pop();
                if (history.isUndoneEmpty()) {
                    return new CommandResult(MESSAGE_FAILURE);
                }
            }
        }
        Command undoneCommand = model.undo();
        if (undoneCommand instanceof TrainingCommand) {
            return new CommandResult(MESSAGE_SUCCESS + undoneCommand
                + " Success!", new Feature("calendar"),
                    ((TrainingCommand) undoneCommand).getDate(), model);
        } else if (undoneCommand instanceof DeleteTrainingCommand) {
            return new CommandResult(MESSAGE_SUCCESS + undoneCommand
                + " Success!", new Feature("calendar"),
                    ((DeleteTrainingCommand) undoneCommand).getDate(), model);
        } else if (undoneCommand instanceof PerformanceCommand) {
            return new CommandResult(MESSAGE_SUCCESS + undoneCommand
                + " Success!", new Feature("calendar"),
                    ((PerformanceCommand) undoneCommand).getDate(), model);
        } else if (undoneCommand instanceof DeleteRecordCommand) {
            return new CommandResult(MESSAGE_SUCCESS + undoneCommand
                + " Success!", new Feature("calendar"),
                    ((DeleteRecordCommand) undoneCommand).getDate(), model);
        } else if (undoneCommand instanceof ClearCommand) {
            return new CommandResult(MESSAGE_SUCCESS + undoneCommand
                + " Success!", new Feature("calendar"), model);
        } else {
            return new CommandResult(MESSAGE_SUCCESS + undoneCommand
                + " Success!");
        }
    }
    @Override
    public boolean isUndoable() {
        return false;
    }
    @Override
    public String toString() {
        return "Undo Command";
    }
}
