package seedu.address.model;

import javafx.util.Pair;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ReversibleCommand;

/**
 * The history manager that keeps track of the application {@code ReversibleCommand} history.
 * This class is used for the undo/redo feature.
 */
public interface CommandHistory {

    /**
     * Returns true if {@code undo(Model model)} has commands to undo.
     */
    boolean canUndo();

    /**
     * Returns true if {@code redo(Model model)} has commands to redo.
     */
    boolean canRedo();

    /**
     * Saves a copy of a {@code ReversibleCommand} at the end of the {@code commandHistoryList}.
     * Undone {@code Reversible} are removed from the {@code commandHistoryList}.
     */
    void commit(ReversibleCommand command);

    /**
     * Returns the undo command of the latest command and the latest command itself.
     *
     * @return {@code CommandResult} from the executed command.
     */
    Pair<Command, ReversibleCommand> getUndoCommand();

    /**
     * Returns the redo command of previously undone Command.
     *
     * @return {@code CommandResult} from the executed command.
     */
    Command getRedoCommand();

    /**
     * Resets the entire command history.
     */
    void reset();
}
