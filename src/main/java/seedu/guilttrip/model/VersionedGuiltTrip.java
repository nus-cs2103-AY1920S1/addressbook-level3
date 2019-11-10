package seedu.guilttrip.model;

import java.util.ArrayList;
import java.util.List;

import seedu.guilttrip.commons.core.step.Step;


/**
 * {@code GuiltTrip} that keeps track of its own history.
 */
public class VersionedGuiltTrip extends GuiltTrip {

    private final List<ReadOnlyGuiltTrip> addressBookStateList;
    private int currentStatePointer;

    public VersionedGuiltTrip(ReadOnlyGuiltTrip initialState) {
        super(initialState);

        addressBookStateList = new ArrayList<>();
        addressBookStateList.add(new GuiltTrip(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code GuiltTrip} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        addressBookStateList.add(new GuiltTrip(this));
        currentStatePointer++;
        indicateModified();
    }

    private void removeStatesAfterCurrentPointer() {
        addressBookStateList.subList(currentStatePointer + 1, addressBookStateList.size()).clear();
    }

    /**
     * Restores the guilttrip book to its previous state.
     */
    public void undo() {
        if (!canUndo(new Step("1"))) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(addressBookStateList.get(currentStatePointer));
    }

    /**
     * Restores the guilttrip book to its previously undone state.
     */
    public void redo() {
        if (!canRedo(new Step("1"))) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(addressBookStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has guilttrip book states to undo.
     */
    public boolean canUndo(Step step) {
        return currentStatePointer >= step.value;
    }

    /**
     * Returns true if {@code redo()} has guilttrip book states to redo.
     */
    public boolean canRedo(Step step) {
        return currentStatePointer + step.value <= addressBookStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedGuiltTrip)) {
            return false;
        }

        VersionedGuiltTrip otherVersionedAddressBook = (VersionedGuiltTrip) other;
        // state check
        return super.equals(otherVersionedAddressBook)
                && addressBookStateList.equals(otherVersionedAddressBook.addressBookStateList)
                && currentStatePointer == otherVersionedAddressBook.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of addressBookState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of addressBookState list, unable to redo.");
        }
    }

}
