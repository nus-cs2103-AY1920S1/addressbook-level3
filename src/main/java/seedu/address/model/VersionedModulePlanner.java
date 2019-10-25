package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.studyplan.UniqueStudyPlanList;
import seedu.address.model.versiontracking.VersionTrackingManager;

/**
 * {@code ModulePlanner} that keeps track of its own history.
 */
public class VersionedModulePlanner extends ModulePlanner {
    private final List<ReadOnlyModulePlanner> historyStateList;
    private int currentStatePointer;

    public VersionedModulePlanner(ReadOnlyModulePlanner toBeCopied, ModulesInfo modulesInfo) {
        super(toBeCopied, modulesInfo);
        historyStateList = new ArrayList<>();
        historyStateList.add(new ModulePlanner(toBeCopied, modulesInfo));
        currentStatePointer = 0;
    }

    /**
     * Creates an ModulePlanner from JSON. This is used in {@code JsonSerializableModulePlanner}.
     */
    public VersionedModulePlanner(UniqueStudyPlanList uniqueStudyPlanList,
                                  ModulesInfo modulesInfo,
                                  VersionTrackingManager versionTrackingManager) {
        super(uniqueStudyPlanList, modulesInfo, versionTrackingManager);
        historyStateList = new ArrayList<>();
        historyStateList.add(new ModulePlanner(uniqueStudyPlanList, modulesInfo, versionTrackingManager));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code AddressBook} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        historyStateList.add(new ModulePlanner(this, super.getModulesInfo()));
        currentStatePointer++;
        // indicateModified();
    }

    private void removeStatesAfterCurrentPointer() {
        historyStateList.subList(currentStatePointer + 1, historyStateList.size()).clear();
    }

    /**
     * Restores the address book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(historyStateList.get(currentStatePointer));
    }

    /**
     * Restores the address book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(historyStateList.get(currentStatePointer));
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
        return currentStatePointer < historyStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedModulePlanner)) {
            return false;
        }

        VersionedModulePlanner otherVersionedModulePlanner = (VersionedModulePlanner) other;

        // state check
        return super.equals(otherVersionedModulePlanner)
                && historyStateList.equals(otherVersionedModulePlanner.historyStateList)
                && currentStatePointer == otherVersionedModulePlanner.currentStatePointer;
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
