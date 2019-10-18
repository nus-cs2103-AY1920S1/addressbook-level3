package seedu.address.model.undo;

import static seedu.address.commons.core.Messages.MESSAGE_NOTHING_TO_REDO;
import static seedu.address.commons.core.Messages.MESSAGE_NOTHING_TO_UNDO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.events.EventSource;
import seedu.address.model.listeners.EventListListener;
import seedu.address.model.listeners.UndoRedoListener;

/**
 * UndoRedoManager contains all EventList states
 * at different points of time in its eventListStateList
 * as well as a currentStateIndex that stores the index of the
 * current EventList state in the list.
 * It also contains a mainEventList that represents the current EventList
 * state. Duplicates of this mainEventList are stored in the eventListStateList.
 * Whenever an undo or redo command is executed, mainEventList restores itself to a
 * past/future state by copying the data in its duplicate over to itself.
 */
public class UndoRedoManager implements EventListListener {

    /**
     * Deep-copies of mainEventList are stored to this list
     * every time a state-changing command is executed.
     * This allows mainEventList to retrieve its data
     * from any of these past or future states when an
     * undo or redo command is called.
     */
    private List<UndoRedoState> undoStateList;
    private int currentStateIndex;

    private List<UndoRedoListener> undoRedoListeners;

    public UndoRedoManager() {
        undoStateList = new ArrayList<>();
        undoStateList.add(new UndoRedoState());
        currentStateIndex = 0;

        undoRedoListeners = new ArrayList<>();
    }

    public void addUndoRedoListener(UndoRedoListener listener) {
        this.undoRedoListeners.add(listener);
    }

    /**
     * Restores the previous event list state from UndoRedoManager.
     */
    public void undo() throws CommandException {
        if (!canUndo()) {
            throw new CommandException(MESSAGE_NOTHING_TO_UNDO);
        }
        currentStateIndex--;
        notifyUndoRedoListeners();
    }

    /**
     * Restores the previously undone event list state from UndoRedoManager.
     */
    public void redo() throws CommandException {
        if (!canRedo()) {
            throw new CommandException(MESSAGE_NOTHING_TO_REDO);
        }
        currentStateIndex++;
        notifyUndoRedoListeners();
    }

    /**
     * Appends a copy of a UndoState to this UndoRedoManager.
     */
    private void commit(UndoRedoState state) {
        assert currentStateIndex >= undoStateList.size() - 1
                : "Pointer always points to end of list during commit; All future states must have been discarded.";
        undoStateList.add(state);
        currentStateIndex++;
    }

    /**
     * Clears all future event list states in eventListStateList beyond the index given by currentStateIndex
     */
    private void clearFutureHistory() {
        undoStateList =
                new ArrayList<>(undoStateList.subList(0, currentStateIndex + 1));
    }

    /**
     * Returns true if there are previous event list states to restore, and false otherwise.
     *
     * @return boolean
     */
    private boolean canUndo() {
        return currentStateIndex > 0;
    }

    /**
     * Returns true if there are future event list states to reset to, and false otherwise.
     *
     * @return boolean
     */
    private boolean canRedo() {
        return currentStateIndex < undoStateList.size() - 1;
    }

    private UndoRedoState getCurrentState() {
        return undoStateList.get(currentStateIndex);
    }

    /**
     * Notify all UndoRedoListeners that undo/redo was called, and provide them with the current UndoState.
     */
    private void notifyUndoRedoListeners() {
        UndoRedoState state = getCurrentState();
        for (UndoRedoListener listener : undoRedoListeners) {
            listener.onUndoRedo(state);
        }
    }

    @Override
    public void onEventListChange(List<EventSource> events) {
        /*
        Ignores the EventList when it is equal to getCurrentState().
        This will be true every undo/redo.
        Explanation: undo/redo will update ModelManager's EventList, which in turn will notify this method,
        causing an unwanted feedback loop.
         */
        if (Objects.equals(getCurrentState().getEvents(), events)) {
            return;
        }

        clearFutureHistory();
        commit(new UndoRedoState(events));
    }

    @Override
    public String toString() {
        return undoStateList.size() + " states in history";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) { // short circuit if same object
            return true;
        } else {
            if (!(other instanceof UndoRedoManager)) {
                return false;
            }
            UndoRedoManager otherHistory = ((UndoRedoManager) other);
            if (currentStateIndex != otherHistory.currentStateIndex
                    || undoStateList.size() != otherHistory.undoStateList.size()) {
                return false;
            }
            for (int i = 0; i < undoStateList.size(); i++) {
                if (!undoStateList.get(i).equals(otherHistory.undoStateList.get(i))) {
                    return false;
                }
            }
            return true;
        }
    }
}
