package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code TutorAid} that keeps track of its own history.
 */
public class VersionedTutorAid extends TutorAid {
    private final List<ReadOnlyTutorAid> tutorAidStateList;
    private int currentStatePointer;

    public VersionedTutorAid(ReadOnlyTutorAid initialState) {
        super(initialState);

        tutorAidStateList = new ArrayList<>();
        tutorAidStateList.add(new TutorAid(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code TutorAid} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        tutorAidStateList.add(new TutorAid(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        tutorAidStateList.subList(currentStatePointer + 1, tutorAidStateList.size()).clear();
    }

    /**
     * Restores the address book to its previous state.
     */
    public void undo() throws NoUndoableStateException {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(tutorAidStateList.get(currentStatePointer));
    }

    /**
     * Restores the address book to its previously undone state.
     */
    public void redo() throws NoRedoableStateException {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(tutorAidStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has address book states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has address book states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < tutorAidStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedTutorAid)) {
            return false;
        }

        VersionedTutorAid otherVersionedTutorAid = (VersionedTutorAid) other;

        // state check
        return super.equals(otherVersionedTutorAid)
                && tutorAidStateList.equals(otherVersionedTutorAid.tutorAidStateList)
                && currentStatePointer == otherVersionedTutorAid.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of tutorAidState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of tutorAidState list, unable to redo.");
        }
    }
}
