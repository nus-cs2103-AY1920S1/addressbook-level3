package seedu.address.logic;

import java.util.Stack;

import seedu.address.logic.events.Event;

/**
 * A command history class keeping track of events that can be undone and events that can be redone.
 */
public class CommandHistory {
    private static Stack<Event> undoEventStack = new Stack<>();
    private static Stack<Event> redoEventStack = new Stack<>();

    /**
     * Checks if undo event stack is empty.
     * @return true if empty, else false.
     */
    public static boolean isEmptyUndoStack() {
        return undoEventStack.empty();
    }

    /**
     * Checks if redo event stack is empty.
     * @return true if empty, else false.
     */
    public static boolean isEmptyRedoStack() {
        return redoEventStack.empty();
    }

    /**
     * Adds new undoable event to the undo event stack.
     * @param undoableEvent Undoable event to be added.
     */
    public static void addToUndoStack(Event undoableEvent) {
        undoEventStack.push(undoableEvent);
    }

    /**
     * Adds new redoable event to the redo event stack.
     * @param redoableEvent Redoable event to be added.
     */
    public static void addToRedoStack(Event redoableEvent) {
        redoEventStack.push(redoableEvent);
    }

    public static void clearRedoStack() {
        redoEventStack.clear();
    }

    /**
     * Retrieves the latest undoable event in the stack.
     * @return Latest undo event.
     */
    public static Event getUndoEvent() {
        return undoEventStack.pop();
    }

    /**
     * Retrieves the latest redoable event in the stack.
     * @return latest redo event.
     */
    public static Event getRedoEvent() {
        return redoEventStack.pop();
    }
}
