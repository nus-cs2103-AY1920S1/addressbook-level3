package seedu.address.model;

import seedu.address.model.exceptions.NoRedoableStateException;
import seedu.address.model.exceptions.NoUndoableStateException;

import java.util.ArrayList;

public class UndoManager extends AddressBook {

    private ArrayList<Memento> mementos = new ArrayList<>();
    private int statePointer;

    public UndoManager() {
        statePointer = 0;
    }

    public void saveState(Memento m) {
        mementos = new ArrayList<>(mementos.subList(0, statePointer + 1));
        mementos.add(m);
        statePointer++;
    }

    public void undo() {
        statePointer--;
        resetData(mementos.get(statePointer).getState());
    }

    public void redo() {
        statePointer++;
        resetData(mementos.get(statePointer).getState());
    }

    public boolean canUndo() {
        return statePointer > 0;
    }

    public boolean canRedo() {
        return statePointer < (mementos.size() - 1);
    }
}
