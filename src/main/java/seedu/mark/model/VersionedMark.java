package seedu.mark.model;

import static seedu.mark.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code Mark} that keeps track of its own history.
 */
public class VersionedMark extends Mark {

    private final List<MarkStateRecord> markStateRecords;
    private int currentPointer;

    public VersionedMark(ReadOnlyMark initialState) {
        super(initialState);

        markStateRecords = new ArrayList<>();
        markStateRecords.add(new MarkStateRecord("", new Mark(initialState)));
        currentPointer = 0;
    }

    /**
     * Appends a copy of the current {@code Mark} state to the end of the state list.
     * Undone states after the current state pointer are removed from the state list.
     * @param record The record for the state
     */
    public void save(String record) {
        removeStatesAfterCurrentPointer();
        markStateRecords.add(new MarkStateRecord(record, new Mark(this)));
        currentPointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        markStateRecords.subList(currentPointer + 1, markStateRecords.size()).clear();
    }

    public String getUndoRecords(int start, int end) {
        StringBuilder records = new StringBuilder();
        for (int i = end; i >= start; i--) {
            records.append(markStateRecords.get(i).getRecord())
                    .append("\n");
        }
        return records.toString();
    }

    public String getRedoRecords(int start, int end) {
        StringBuilder records = new StringBuilder();
        for (int i = start; i <= end; i++) {
            records.append(markStateRecords.get(i).getRecord())
                    .append("\n");
        }
        return records.toString();
    }

    /**
     * Restores the Mark to its previous state.
     * @param steps
     */
    public String undo(int steps) {
        if (!canUndo(steps)) {
            throw new CannotUndoMarkException();
        }
        String records = getUndoRecords(currentPointer - steps + 1, currentPointer);
        currentPointer = currentPointer - steps;
        resetData(markStateRecords.get(currentPointer).getState());
        return records;
    }

    /**
     * Restores the Mark to its previously undone state.
     * @param steps
     */
    public String redo(int steps) {
        if (!canRedo(steps)) {
            throw new CannotRedoMarkException();
        }
        String records = getRedoRecords(currentPointer + 1, currentPointer + steps);
        currentPointer = currentPointer + steps;
        resetData(markStateRecords.get(currentPointer).getState());
        return records;
    }

    /**
     * Returns true if {@code undo()} has Mark states to undo.
     * @param steps
     */
    public boolean canUndo(int steps) {
        return currentPointer > (steps - 1);
    }

    public int getMaxStepsToUndo() {
        return currentPointer;
    }

    /**
     * Returns true if {@code redo()} has Mark states to redo.
     * @param steps
     */
    public boolean canRedo(int steps) {
        return currentPointer + steps < markStateRecords.size();
    }

    public int getMaxStepsToRedo() {
        return markStateRecords.size() - currentPointer - 1;
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
                && markStateRecords.equals(otherVersionedMark.markStateRecords)
                && currentPointer == otherVersionedMark.currentPointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class CannotUndoMarkException extends RuntimeException {
        private CannotUndoMarkException() {
            super("Current state pointer at start of markState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class CannotRedoMarkException extends RuntimeException {
        private CannotRedoMarkException() {
            super("Current state pointer at end of markState list, unable to redo.");
        }
    }

    /**
     * Represents a state record for Mark.
     */
    public static class MarkStateRecord {
        /** Record about which action leads to the state **/
        private String record;
        private ReadOnlyMark state;

        public MarkStateRecord(String record, ReadOnlyMark state) {
            requireAllNonNull(record, state);

            this.record = record;
            this.state = state;
        }

        public ReadOnlyMark getState() {
            return state;
        }

        public String getRecord() {
            return record;
        }

        @Override
        public boolean equals(Object other) {
            return other == this // short circuit if same object
                    || (other instanceof MarkStateRecord // instanceof handles nulls
                    && record.equals(((MarkStateRecord) other).record)
                    && state.equals(((MarkStateRecord) other).state)); // state check
        }
    }
}
