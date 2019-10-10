package com.typee.model;

import java.util.LinkedList;
import java.util.List;

import com.typee.logic.commands.exceptions.NullRedoableActionException;
import com.typee.logic.commands.exceptions.NullUndoableActionException;

/**
 * {@code AppointmentList} with a list of its previous states.
 */
public class StatedAddressBook extends AddressBook {

    private final List<ReadOnlyAddressBook> historyStateList;
    private int versionPointer;

    public StatedAddressBook(ReadOnlyAddressBook initialList) {
        super(initialList);

        historyStateList = new LinkedList<>();
        historyStateList.add(new AddressBook(initialList));
        versionPointer = 0;
    }

    /**
     * Reverts the list to its previous state.
     */
    public void undo() throws NullUndoableActionException {
        if (!isUndoable()) {
            throw new NullUndoableActionException();
        }
        versionPointer--;
        resetData(historyStateList.get(versionPointer));
    }

    /**
     * Reverts the list to its previously undone state.
     */
    public void redo() throws NullRedoableActionException {
        if (!isRedoable()) {
            throw new NullRedoableActionException();
        }
        versionPointer++;
        resetData(historyStateList.get(versionPointer));
    }

    public boolean isUndoable() {
        return versionPointer > 0;
    }

    public boolean isRedoable() {
        return versionPointer < historyStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof StatedAddressBook)) {
            return false;
        }

        StatedAddressBook otherStatedAppointmentList = (StatedAddressBook) other;

        return super.equals(otherStatedAppointmentList)
                && versionPointer == otherStatedAppointmentList.versionPointer;
    }
}
