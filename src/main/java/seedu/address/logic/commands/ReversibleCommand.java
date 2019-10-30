package seedu.address.logic.commands;

/**
 * A command that can be undone.
 */
public interface ReversibleCommand {

    /**
     * Returns a command that undo this command.
     *
     * @return a command that undo what this command does.
     */
    Command getUndoCommand();

    /**
     * Returns a command that redo this command.
     *
     * @return a command that redo what this command does.
     */
    Command getRedoCommand();

}
