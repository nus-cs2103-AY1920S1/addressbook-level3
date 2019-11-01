package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code UserState} that keeps track of its own history.
 */
public class VersionedUserState extends UserState {

    private final List<ReadOnlyUserState> userStateList;
    private int currentStatePointer;

    public VersionedUserState(ReadOnlyUserState initialState) {
        super(initialState);

        userStateList = new ArrayList<>();
        userStateList.add(new UserState(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code UserState} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        userStateList.add(new UserState(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        userStateList.subList(currentStatePointer + 1, userStateList.size()).clear();
    }

    /**
     * Restores the user state to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(userStateList.get(currentStatePointer));
    }

    /**
     * Restores the user state to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(userStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has user state states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has user state states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < userStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedUserState)) {
            return false;
        }

        VersionedUserState otherVersionedUserState = (VersionedUserState) other;

        // state check
        return super.equals(otherVersionedUserState)
            && userStateList.equals(otherVersionedUserState.userStateList)
            && currentStatePointer == otherVersionedUserState.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of bankAccountState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of bankAccountState list, unable to redo.");
        }
    }
}
