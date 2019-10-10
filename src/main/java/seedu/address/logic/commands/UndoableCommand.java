package seedu.address.logic.commands;

/**
 * Represents a particular type of command that is undoable.
 */
public abstract class UndoableCommand extends Command {
    public UndoableCommand() {
        super(true);
    }
}
