package seedu.exercise.logic.commands;

/**
 * Represents a command that can be undone.
 */
public interface UndoableCommand {

    /**
     * Returns the command word that is used to call the undoable command.
     *
     * @return command word of the undoable command
     */
    String getUndoableCommandWord();
}
