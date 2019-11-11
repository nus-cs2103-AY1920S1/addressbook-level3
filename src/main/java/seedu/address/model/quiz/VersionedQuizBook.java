package seedu.address.model.quiz;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code AddressQuizBook} that keeps track of its own history.
 */
public class VersionedQuizBook extends AddressQuizBook {

    private final List<ReadOnlyQuizBook> quizBookStateList;
    private int currentStatePointer;

    public VersionedQuizBook(ReadOnlyQuizBook initialState) {
        super(initialState);

        quizBookStateList = new ArrayList<>();
        quizBookStateList.add(new AddressQuizBook(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code AddressQuizBook} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        quizBookStateList.add(new AddressQuizBook(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        quizBookStateList.subList(currentStatePointer + 1, quizBookStateList.size()).clear();
    }

    /**
     * Restores the quiz book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(quizBookStateList.get(currentStatePointer));
    }

    /**
     * Restores the quiz book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(quizBookStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has quiz book states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has quiz book states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < quizBookStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedQuizBook)) {
            return false;
        }

        VersionedQuizBook otherVersionedQuizBook = (VersionedQuizBook) other;

        // state check
        return super.equals(otherVersionedQuizBook)
                && quizBookStateList.equals(otherVersionedQuizBook.quizBookStateList)
                && currentStatePointer == otherVersionedQuizBook.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of quizBookState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of quizBookState list, unable to redo.");
        }
    }
}
