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

    public static final String MESSAGE_ENTITY_NOT_FOUND = "The entity with the specified identification number"
            + " was not found.";
    public static final String MESSAGE_NOT_EXECUTED_BEFORE = "Command cannot be undone before it is "
            + "successfully executed.";
    public static final String MESSAGE_NOT_UNDONE_BEFORE = "Command cannot be redone before it is successfully undone!";

    private UndoableCommandState commandState = UndoableCommandState.PRE_EXECUTION;

    public abstract CommandResult undo(Model model) throws CommandException;


    /**
     * Re-executes the command that was previously undone.
     */
    public CommandResult redo(Model model) throws CommandException {
        if (getCommandState() != UndoableCommandState.REDOABLE) {
            return new CommandResult(MESSAGE_NOT_UNDONE_BEFORE);
        }
        return execute(model);
    }

    /**
     * Sets the command status to Undoable. This is done only after a command is executed.
     */
    public void setUndoable() {
        commandState = UndoableCommandState.UNDOABLE;
    }

    /**
     * Sets the command status to Redoable. This is done after a command is undone.
     */
    public void setRedoable() {
        commandState = UndoableCommandState.REDOABLE;
    }

    /**
     * Returns the status of the command, allowing UndoableCommands to check if undoing or redoing is a valid action.
     * @return
     */
    public UndoableCommandState getCommandState() {
        return commandState;
    }
}
