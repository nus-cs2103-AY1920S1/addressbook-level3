package seedu.address.logic.commands;

/**
 * Represent a command then can be undone.
 */
public abstract class UndoableCommand extends Command {
    /**
     * Gets the command word of the undoable command.
     * @return command word of the undoable command.
     */
    public abstract String getCommandWord();
}
