package seedu.address.logic.commands;

/**
 * A command that can be undone.
 */
public abstract class ReversibleCommand extends Command {

    protected Command undoCommand;
    protected Command redoCommand;
    protected CommandResult commandResult;

    /**
     * Returns a command that undo this command.
     *
     * @return a command that undo what this command does.
     */
    public Command getUndoCommand() {
        return undoCommand;
    };

    /**
     * Returns a command that redo this command.
     *
     * @return a command that redo what this command does.
     */
    public Command getRedoCommand() {
        return redoCommand;
    }

    /**
     * Returns the {@code CommandResult} of the executed command.
     * Used to give feedback to users.
     *
     * @return a {@code CommandResult} of the executed command.
     */
    public CommandResult getCommandResult() {
        return commandResult;
    }
}
