package seedu.flashcard.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code AddressBook} that keeps track of its own history.
 */
public class VersionedFlashcardList extends FlashcardList {

    private final List<ReadOnlyFlashcardList> flashcardListStateList;
    private int currentStatePointer;

    public VersionedFlashcardList(ReadOnlyFlashcardList initialState) {
        super(initialState);

        flashcardListStateList = new ArrayList<>();
        flashcardListStateList.add(new FlashcardList(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code FlashcardList} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        flashcardListStateList.add(new FlashcardList(this));
        currentStatePointer++;
        indicateModified();
    }

    private void removeStatesAfterCurrentPointer() {
        flashcardListStateList.subList(currentStatePointer + 1, flashcardListStateList.size()).clear();
    }

    /**
     * Restores the flashcard list to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(flashcardListStateList.get(currentStatePointer));
    }

    /**
     * Restores the flashcard list to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(flashcardListStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has flashcard list states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has flashcard list states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < flashcardListStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedFlashcardList)) {
            return false;
        }

        VersionedFlashcardList otherVersionedFlashcardList = (VersionedFlashcardList) other;

        // state check
        return super.equals(otherVersionedFlashcardList)
                && flashcardListStateList.equals(otherVersionedFlashcardList.flashcardListStateList)
                && currentStatePointer == otherVersionedFlashcardList.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of flashcardListState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of flashcardListState list, unable to redo.");
        }
    }
}
