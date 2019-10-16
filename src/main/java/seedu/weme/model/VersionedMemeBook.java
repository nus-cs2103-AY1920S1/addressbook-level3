package seedu.weme.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code MemeBook} that keeps track of it's previous states.
 */
public class VersionedMemeBook extends MemeBook {

    private final List<ReadOnlyMemeBook> versionedMemeBookStates;
    private int stateIndex = 0;

    public VersionedMemeBook(ReadOnlyMemeBook initialState) {
        super(initialState);

        versionedMemeBookStates = new ArrayList<>();
        versionedMemeBookStates.add(new MemeBook(initialState));
    }

    /**
     * Returns true if has previous states to undo to.
     */
    public boolean canUndo() {
        return stateIndex > 0;
    }

    /**
     * Returns true if has previously undone states to redo to.
     */
    public boolean canRedo() {
        return stateIndex < versionedMemeBookStates.size() - 1;
    }

    /**
     * Saves the current state to the end of the state list.
     * Wipes previously undone states.
     */
    public void commit() {
        versionedMemeBookStates.subList(stateIndex + 1, versionedMemeBookStates.size()).clear();
        versionedMemeBookStates.add(new MemeBook(this));
        stateIndex++;
    }

    /**
     * Restores the meme book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        stateIndex--;
        resetData(versionedMemeBookStates.get(stateIndex));
    }

    /**
     * Restores the meme book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        stateIndex++;
        resetData(versionedMemeBookStates.get(stateIndex));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof VersionedMemeBook)) {
            return false;
        }

        VersionedMemeBook otherMemeBook = (VersionedMemeBook) other;

        return super.equals(otherMemeBook)
                && versionedMemeBookStates.equals(otherMemeBook.versionedMemeBookStates)
                && stateIndex == otherMemeBook.stateIndex;
    }

    /**
     * Thrown when unable to undo.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("There are no commands to undo.");
        }
    }

    /**
     * Thrown when unable to redo.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("There are no commands to redo.");
        }
    }
}
