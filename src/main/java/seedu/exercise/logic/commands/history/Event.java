package seedu.exercise.logic.commands.history;

import seedu.exercise.model.Model;

/**
 * Represents an Event that can be undone or redone.
 */
public interface Event {

    /**
     * Executes the reverse of the event.
     *
     * @param model {@code Model} which the command should operate on.
     */
    void undo(Model model);

    /**
     * Executes the event again.
     *
     * @param model {@code Model} which the command should operate on.
     */
    void redo(Model model);
}
