package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.history.HistoryManager;

/**
 * Undo the most recent undoable Command.
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo Command Success";
    public static final String MESSAGE_FAILURE = "Undo Command Failure:"
        + " No available commands to be undone. "
        + " Commands that can be undone is as follows: add, delete, edit, clear and training.";
    @Override
    public CommandResult execute(Model model) throws CommandException {
        HistoryManager history = new HistoryManager();
        if (history.isUndoneEmpty()) {
            return new CommandResult(MESSAGE_FAILURE);
        }
        while (!history.getLatestCommand().isUndoable()) {
            System.out.println(history.getLatestCommand());
            if (history.isUndoneEmpty()) {
                return new CommandResult(MESSAGE_FAILURE);
            } else {
                System.out.println(HistoryManager.getCommands().pop());
                System.out.println(HistoryManager.getAddressBooks().pop());
                if (history.isUndoneEmpty()) {
                    return new CommandResult(MESSAGE_FAILURE);
                }
            }
        }
        Command undoneCommand = model.undo();
        if (undoneCommand instanceof TrainingCommand) {
            return new CommandResult(MESSAGE_SUCCESS, ((TrainingCommand) undoneCommand).getDate(), model);
        } else {
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }
    @Override
    public boolean isUndoable() {
        return false;
    }
}
