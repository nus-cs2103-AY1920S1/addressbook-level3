package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.util.Pair;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ReversibleCommand;

/**
 * The history manager that keeps track of the application {@code ReversibleCommand} history.
 * This class is used for the undo/redo feature.
 */
public class CommandHistoryManager implements CommandHistory {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final List<ReversibleCommand> commandHistoryList;

    // Points to next undoable command.
    private int currentCommandPointer;

    public CommandHistoryManager() {
        commandHistoryList = new ArrayList<>();
        currentCommandPointer = -1;
    }

    /**
     * Saves a copy of a {@code ReversibleCommand} at the end of the {@code commandHistoryList}.
     * Undone {@code Reversible} are removed from the {@code commandHistoryList}.
     */
    @Override
    public void commit(ReversibleCommand command) {
        requireNonNull(command);
        logger.info("Committing reversible command into the command history.");

        logger.finest("Removing all commands after current pointer.");
        removeStatesAfterCurrentPointer();
        logger.finest("Remove is successful.");

        logger.finest("Adding command to the end command history list.");
        commandHistoryList.add(command);
        logger.finest("Command is successfully added to the command history list.");

        logger.finest("Incrementing pointer.");
        currentCommandPointer++;
        logger.finest("Pointer is successfully incremented");

        logger.info("Command is successfully committed into the command history.");
    }

    private void removeStatesAfterCurrentPointer() {
        commandHistoryList.subList(currentCommandPointer + 1, commandHistoryList.size()).clear();
    }

    /**
     * Returns the undo command of the latest command and the latest command itself.
     *
     * @return {@code CommandResult} from the executed command.
     */
    @Override
    public Pair<Command, ReversibleCommand> getUndoCommand() {
        if (!canUndo()) {
            throw new NoUndoableCommandException();
        }

        logger.info("Retrieving undo command from commandHistoryList.");

        ReversibleCommand commandToUndo = commandHistoryList.get(currentCommandPointer);
        assert commandToUndo != null : "All reversible commands committed should have been null checked.";

        Command command = commandToUndo.getUndoCommand();
        assert command != null : "Undo Command should have been assigned once the command was executed.";

        currentCommandPointer--;

        return new Pair<>(command, commandToUndo);
    }

    /**
     * Returns the redo command of previously undone Command.
     *
     * @return {@code CommandResult} from the executed command.
     */
    public Command getRedoCommand() {
        if (!canRedo()) {
            throw new NoRedoableCommandException();
        }

        logger.info("Retrieving redo command from commandHistoryList.");

        currentCommandPointer++;

        ReversibleCommand commandToRedo = commandHistoryList.get(currentCommandPointer);
        assert commandToRedo != null : "All reversible commands committed should have been null checked.";

        Command redoCommand = commandToRedo.getRedoCommand();
        assert redoCommand != null : "Redo Command should have been assigned once the command was executed.";

        return redoCommand;
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

    /**
     * Resets the entire command history.
     */
    public void reset() {
        logger.info("Resetting commandHistoryList.");
        commandHistoryList.clear();
        currentCommandPointer = -1;
        logger.info("Resetting of commandHistoryList is completed.");
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandHistoryManager)) {
            return false;
        }

        CommandHistoryManager otherCommandHistoryManager = (CommandHistoryManager) other;

        // state check
        return commandHistoryList.equals(otherCommandHistoryManager.commandHistoryList)
                && currentCommandPointer == otherCommandHistoryManager.currentCommandPointer;
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
