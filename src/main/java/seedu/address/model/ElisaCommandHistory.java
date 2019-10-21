package seedu.address.model;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.UndoableCommand;

/**
 * interface for state history
 */

public interface ElisaCommandHistory {
    public void pushCommand(Command command);

    public UndoableCommand popCommand();

    public UndoableCommand peekCommand();

    public int size();
}
