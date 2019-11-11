package seedu.weme.model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import seedu.weme.commons.core.LogsCenter;

/**
 * {@code Weme} that keeps track of it's previous states.
 */
public class VersionedWeme extends Weme {

    private static final Logger logger = LogsCenter.getLogger(VersionedWeme.class);
    private final List<ReadOnlyWeme> versionedWemeStates;
    private int stateIndex = 0;
    private final List<String> feedbackList = new ArrayList<>(); // Feedback for undo redo command. 1 step slow.

    public VersionedWeme(ReadOnlyWeme initialState) {
        super(initialState);

        versionedWemeStates = new ArrayList<>();
        versionedWemeStates.add(new Weme(initialState));
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
        return stateIndex < versionedWemeStates.size() - 1;
    }

    /**
     * Saves the current state to the end of the state list.
     * Wipes previously undone states.
     * @param feedback the feedback of the last executed command
     */
    public void commit(String feedback) {
        assert feedback != null;

        clearExtraWemeStates();
        versionedWemeStates.add(new Weme(this));
        feedbackList.add(feedback);
        stateIndex++;
        logger.info("Successfully added state to history");
    }

    /**
     * Restores Weme to its previous state.
     * @return the feedback to the user of the command just undone
     */
    public String undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        assert stateIndex > 0;

        stateIndex--;
        resetData(versionedWemeStates.get(stateIndex));
        logger.info("Successfully moved back the Weme state");
        return feedbackList.get(stateIndex);
    }

    /**
     * Restores Weme to its previously undone state.
     * @return the feedback to the user of the command just redone
     */
    public String redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        assert stateIndex < versionedWemeStates.size() - 1;

        stateIndex++;
        resetData(versionedWemeStates.get(stateIndex));
        logger.info("Successfully moved forward the Weme state");
        return feedbackList.get(stateIndex - 1);
    }

    private void clearExtraWemeStates() {
        versionedWemeStates.subList(stateIndex + 1, versionedWemeStates.size()).clear();
        feedbackList.subList(stateIndex, feedbackList.size()).clear();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof VersionedWeme)) {
            return false;
        }

        VersionedWeme otherWeme = (VersionedWeme) other;

        return super.equals(otherWeme)
                && versionedWemeStates.equals(otherWeme.versionedWemeStates)
                && stateIndex == otherWeme.stateIndex
                && feedbackList.equals(otherWeme.feedbackList);
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
