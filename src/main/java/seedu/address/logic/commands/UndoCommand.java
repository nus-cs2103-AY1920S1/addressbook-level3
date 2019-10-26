package seedu.address.logic.commands;

import seedu.address.model.history.HistoryManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class UndoCommand extends Command{
    
    public static final String COMMAND_WORD = "undo";
    
    private static final String MESSAGE_SUCCESS = "Undo Command Success";
    private static final String MESSAGE_FAILURE = "Undo Command Failure: No available commands to be undone. " +
        " Commands that can be undone is as follows: add, delete, edit.";
    
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
                System.out.println(HistoryManager.commands.pop());
                System.out.println(HistoryManager.addressBooks.pop());
                if (history.isUndoneEmpty()) {
                    return new CommandResult(MESSAGE_FAILURE);
                }
            }
        }
        model.undo();
        return new CommandResult(MESSAGE_SUCCESS);
    }
    
    @Override
    public boolean isUndoable() {
        return false;
    }
}
