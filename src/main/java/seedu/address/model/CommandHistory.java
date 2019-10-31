package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * The history manager that keeps track of the application {@code ReversibleCommand} history.
 * This class is used for the undo/redo feature.
 */
public class CommandHistory {
    private final List<ReversibleCommand> commandHistoryList;

    // Points to next undoable command.
    private int currentCommandPointer;

    public CommandHistory() {
        commandHistoryList = new ArrayList<>();
        currentCommandPointer = -1;
    }

    /**
     * Saves a copy of a {@code ReversibleCommand} at the end of the {@code commandHistoryList}.
     * Undone {@code Reversible} are removed from the {@code commandHistoryList}.
     */
    public void commit(ReversibleCommand command) {
        removeStatesAfterCurrentPointer();
        commandHistoryList.add(command);
        currentCommandPointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        commandHistoryList.subList(currentCommandPointer + 1, commandHistoryList.size()).clear();
    }

    /**
     * Undoes the latest command.
     *
     * @param model which the command is executed on.
     */
    public void undo(Model model) {
        if (!canUndo()) {
            throw new NoUndoableCommandException();
        }
        try {
            commandHistoryList.get(currentCommandPointer).getUndoCommand().execute(model);
        } catch (CommandException e) {
            assert false;
        }
        currentCommandPointer--;
    }

    /**
     * Redoes the previously undone Command.
     *
     * @param model which the command is executed on.
     */
    public void redo(Model model) {
        if (!canRedo()) {
            throw new NoRedoableCommandException();
        }
        currentCommandPointer++;
        try {
            commandHistoryList.get(currentCommandPointer).getRedoCommand().execute(model);
        } catch (CommandException e) {
            assert false;
        }
    }

    /**
     * Returns true if {@code undo(Model model)} has commands to undo.
     */
    public boolean canUndo() {
        return currentCommandPointer >= 0;
    }

    /**
     * Returns true if {@code redo(Model model)} has commands to redo.
     */
    public boolean canRedo() {
        return currentCommandPointer < commandHistoryList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandHistory)) {
            return false;
        }

        CommandHistory otherCommandHistory = (CommandHistory) other;

        // state check
        return commandHistoryList.equals(otherCommandHistory.commandHistoryList)
                && currentCommandPointer == otherCommandHistory.currentCommandPointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableCommandException extends RuntimeException {
        private NoUndoableCommandException() {
            super("Current command pointer does not point to any reversible command, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableCommandException extends RuntimeException {
        private NoRedoableCommandException() {
            super("Current command pointer is at end of command history list, no command to redo.");
        }
    }
}
