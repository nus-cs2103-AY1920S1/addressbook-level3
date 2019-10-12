package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

//@@author ambervoong
/**
 * Represents a command that can be undone or redone, undoing or redoing changes to Mortago made by the command.
 * Only commands that change program state can be undone
 */
public abstract class UndoableCommand extends Command {

    /**
     * Enumerates through the possible states of an UndoableCommand.
     */
    public enum UndoableCommandState {
        UNDOABLE, REDOABLE, PRE_EXECUTION
    }

    public static final String MESSAGE_UNDO_FAIL = "Command cannot be undone before it is successfully executed.";
    public static final String MESSAGE_REDO_FAIL = "Command cannot be redone before it is successfully undone!";

    private UndoableCommandState commandState = UndoableCommandState.PRE_EXECUTION;

    public abstract CommandResult undo(Model model) throws CommandException;

    /**
     * Re-executes the command that was previously undone.
     */
    public CommandResult redo(Model model) throws CommandException {
        if (getCommandState() != UndoableCommandState.REDOABLE) {
            return new CommandResult(MESSAGE_UNDO_FAIL);
        }
        return execute(model);
    }

    public void setUndoable() {
        commandState = UndoableCommandState.UNDOABLE;
    }

    public void setRedoable() {
        commandState = UndoableCommandState.REDOABLE;
    }

    public UndoableCommandState getCommandState() {
        return commandState;
    }
}
