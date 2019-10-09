package seedu.mark.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code Mark} that keeps track of its own history.
 */
public class VersionedMark extends Mark {

    private final List<ReadOnlyMark> markStateList;
    private int currentStatePointer;

    public VersionedMark(ReadOnlyMark initialState) {
        super(initialState);

        markStateList = new ArrayList<>();
        markStateList.add(new Mark(initialState));
        currentStatePointer = 0;
    }

    /**
     * Appends a copy of the current {@code Mark} state to the end of the state list.
     * Undone states after the current state pointer are removed from the state list.
     */
    public void save() {
        removeStatesAfterCurrentPointer();
        markStateList.add(new Mark(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        markStateList.subList(currentStatePointer + 1, markStateList.size()).clear();
    }

    /**
     * Restores the mark to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new CannotUndoMarkException();
        }
        currentStatePointer--;
        resetData(markStateList.get(currentStatePointer));
    }

    /**
     * Restores the mark to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new CannotRedoMarkException();
        }
        currentStatePointer++;
        resetData(markStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has mark states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has mark states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < markStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedMark)) {
            return false;
        }

        VersionedMark otherVersionedMark = (VersionedMark) other;

        // state check
        return super.equals(otherVersionedMark)
                && markStateList.equals(otherVersionedMark.markStateList)
                && currentStatePointer == otherVersionedMark.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    private static class CannotUndoMarkException extends RuntimeException {
        private CannotUndoMarkException() {
            super("Current state pointer at start of markState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    private static class CannotRedoMarkException extends RuntimeException {
        private CannotRedoMarkException() {
            super("Current state pointer at end of markState list, unable to redo.");
        }
    }
}
