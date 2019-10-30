package seedu.address.logic.events;

import seedu.address.logic.commands.UndoableCommand;

/**
 * Represents events that can be undone.
 */
public interface Event {
    /**
     * A method to undo the effects of the event.
     * @return Returns a CommandResult to undo the effects of the event.
     */
    UndoableCommand undo();

    /**
     * A method to redo the effects of the event undone.
     * @return Returns a CommandResult to redo the effects of the event that was previously undone.
     */
    UndoableCommand redo();
}
