package seedu.address.logic;

import static seedu.address.commons.core.Messages.MESSAGE_NOTHING_TO_REDO;
import static seedu.address.commons.core.Messages.MESSAGE_NOTHING_TO_UNDO;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelData;
import seedu.address.model.ModelManager;
import seedu.address.model.listeners.ModelDataListener;

/**
 * UndoRedoManager contains all EventList states
 * at different points of time in its eventListStateList
 * as well as a currentStateIndex that stores the index of the
 * current EventList state in the list.
 * It also contains a mainEventList that represents the current EventList
 * state. Duplicates of this mainEventList are stored in the eventListStateList.
 * Whenever an undo or redo command is executed, mainEventList restores itself to a
 * past/future state by copying the data in its duplicate over to itself.
 */
public class UndoRedoManager implements ModelDataListener {

    /**
     * Deep-copies of Model are stored to this list
     * every time a state-changing command is executed.
     * This allows mainEventList to retrieve its data
     * from any of these past or future states when an
     * undo or redo command is called.
     */
    private final List<ModelData> undoStateList;
    private final ModelManager model;

    private boolean listening;
    private int undoIndex;

    public UndoRedoManager(ModelManager model) {
        this.undoStateList = new ArrayList<>();
        this.model = model;
    }

    /**
     * This method should be called first.
     * Initializes the UndoRedoManager with the Model's initial data.
     * Allows UndoRedoManager to start listening to ModelList changes.
     */
    public void start() {
        undoStateList.add(this.model.getModelData());
        listening = true;
        undoIndex = 0;
    }

    /**
     * Restores the previous ModelList from UndoRedoManager.
     */
    public void undo() throws CommandException {
        if (!canUndo()) {
            throw new CommandException(MESSAGE_NOTHING_TO_UNDO);
        }
        undoIndex--;
        notifyModelResetListeners();
    }

    /**
     * Restores the previously undone ModelList from UndoRedoManager.
     */
    public void redo() throws CommandException {
        if (!canRedo()) {
            throw new CommandException(MESSAGE_NOTHING_TO_REDO);
        }
        undoIndex++;
        notifyModelResetListeners();
    }

    /**
     * Clears all future event list states in eventListStateList beyond the index given by currentStateIndex
     */
    private void clearFutureHistory() {
        List<ModelData> subList = new ArrayList<>(undoStateList.subList(0, undoIndex + 1));
        undoStateList.clear();
        undoStateList.addAll(subList);
    }

    /**
     * Returns true if there are previous event list states to restore, and false otherwise.
     *
     * @return boolean
     */
    private boolean canUndo() {
        return undoIndex > 0;
    }

    /**
     * Returns true if there are future event list states to reset to, and false otherwise.
     *
     * @return boolean
     */
    private boolean canRedo() {
        return undoIndex < undoStateList.size() - 1;
    }

    /**
     * Notify all UndoRedoListeners that undo/redo was called, and provide them with the current UndoState.
     */
    private void notifyModelResetListeners() {
        if (listening) {
            // Disable listening to prevent feedback loop from model manager
            listening = false;
            model.setModelData(undoStateList.get(undoIndex));
            listening = true;
        }
    }

    /**
     * Appends the current ModelLists, containing the current events and tasks, to undoStateList.
            */
    @Override
    public void onModelDataChange(ModelData modelData) {
        if (listening) {
            clearFutureHistory();
            assert undoIndex >= undoStateList.size() - 1
                : "Pointer always points to end of list during commit; All future states must have been discarded.";
            undoStateList.add(modelData);
            undoIndex++;
        }
    }

    @Override
    public String toString() {
        return undoStateList.size() + " states in history";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) { // short circuit if same object
            return true;
        } else {
            if (!(other instanceof UndoRedoManager)) {
                return false;
            }
            UndoRedoManager otherHistory = ((UndoRedoManager) other);
            if (undoIndex != otherHistory.undoIndex
                    || undoStateList.size() != otherHistory.undoStateList.size()) {
                return false;
            }
            for (int i = 0; i < undoStateList.size(); i++) {
                if (!undoStateList.get(i).equals(otherHistory.undoStateList.get(i))) {
                    return false;
                }
            }
            return true;
        }
    }

    public List<ModelData> getUndoStateList() {
        return undoStateList;
    }

    public int getUndoIndex() {
        return undoIndex;
    }

}
