package seedu.address.model.listeners;

import seedu.address.model.undo.UndoRedoState;

/**
 * Represents a listener that will be notified whenever the currentStateIndex in UndoRedoManager changes.
 */
public interface UndoRedoListener {

    void onUndoRedo(UndoRedoState state);
}
