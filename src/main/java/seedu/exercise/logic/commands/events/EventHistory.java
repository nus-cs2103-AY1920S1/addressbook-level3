package seedu.exercise.logic.commands.events;

import java.util.Stack;
import java.util.logging.Logger;

import seedu.exercise.commons.core.LogsCenter;
import seedu.exercise.logic.commands.UndoableCommand;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.Model;

/**
 * A singleton class that tracks a single history of undoable events.
 */
public class EventHistory {

    public static final String MESSAGE_ADD_TO_EVENT_HISTORY_SUCCESS =
            "Command added to Event History: %1$s";
    private static final Logger logger = LogsCenter.getLogger(EventHistory.class);
    private static EventHistory eventHistory;
    private Stack<Event> undoStack;
    private Stack<Event> redoStack;

    /**
     * Initializes both undo and redo history if no undo history exists.
     */
    private EventHistory() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    /**
     * Returns an EventHistory object that tracks the history of undoable events.
     *
     * @return an instance of EventHistory that can be used to access the undo and redo history.
     */
    public static EventHistory getInstance() {
        if (eventHistory == null) {
            eventHistory = new EventHistory();
        }
        return eventHistory;
    }

    /**
     * Stores a command as an event in the EventHistory.
     *
     * @param command an undoable command to be stored in history
     * @throws CommandException if {@code command} cannot be represented with an {@code Event}
     */
    public void addCommandToUndoStack(UndoableCommand command) throws CommandException {
        Event event = EventFactory.commandToEvent(command);
        undoStack.add(event);
        redoStack.clear();
        logger.info(String.format(MESSAGE_ADD_TO_EVENT_HISTORY_SUCCESS, command.getUndoableCommandWord()));
    }

    /**
     * Returns the next event to undo.
     *
     * @param model {@code Model} which the command should operate on.
     * @return undoable event
     */
    public Event undo(Model model) {
        assert(!undoStack.isEmpty());
        Event eventToUndo = undoStack.pop();
        eventToUndo.undo(model);
        redoStack.push(eventToUndo);
        return eventToUndo;
    }

    /**
     * Returns the next event to redo.
     *
     * @param model {@code Model} which the command should operate on.
     * @return undoable event
     */
    public Event redo(Model model) {
        assert(!redoStack.isEmpty());
        Event eventToRedo = redoStack.pop();
        eventToRedo.redo(model);
        undoStack.push(eventToRedo);
        return eventToRedo;
    }

    /**
     * Clears both undo and redo history.
     */
    public void reset() {
        undoStack.clear();
        redoStack.clear();
    }

    /**
     * Checks if the undo history is empty.
     *
     * @return true if undo stack is empty, false otherwise
     */
    public boolean isUndoStackEmpty() {
        return undoStack.isEmpty();
    }

    /**
     * Checks if the redo history is empty.
     *
     * @return true if redo stack is empty, false otherwise
     */
    public boolean isRedoStackEmpty() {
        return redoStack.isEmpty();
    }

    public Stack<Event> getUndoStack() {
        return undoStack;
    }

    public Stack<Event> getRedoStack() {
        return redoStack;
    }
}
