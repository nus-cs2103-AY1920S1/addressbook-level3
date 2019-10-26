package seedu.elisa.model;

import seedu.elisa.logic.commands.Command;
import seedu.elisa.logic.commands.UndoableCommand;

/**
 * interface for state history
 */

public interface ElisaCommandHistory {
    public void pushCommand(Command command);

    public UndoableCommand popCommand();

    public UndoableCommand peekCommand();

    public int size();
}
