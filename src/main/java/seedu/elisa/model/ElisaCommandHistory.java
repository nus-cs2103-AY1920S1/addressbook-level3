package seedu.elisa.model;

import seedu.elisa.logic.commands.Command;
import seedu.elisa.logic.commands.UndoableCommand;

/**
 * interface for state history
 */

public interface ElisaCommandHistory {
    public void pushUndo(Command command);

    public UndoableCommand popUndo();

    public UndoableCommand peekUndo();

    public int sizeUndo();

    public void pushRedo(Command command);

    public UndoableCommand popRedo();

    public UndoableCommand peekRedo();

    public int sizeRedo();

    public void clearRedo();
}
