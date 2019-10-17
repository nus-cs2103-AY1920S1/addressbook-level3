package com.typee.model;

import java.util.LinkedList;
import java.util.List;

import com.typee.logic.commands.exceptions.NullRedoableActionException;
import com.typee.logic.commands.exceptions.NullUndoableActionException;

/**
 * {@code AppointmentList} with a list of its previous states.
 */
public class HistoryManager extends EngagementList {

    private final List<ReadOnlyEngagementList> historyBook;
    private int versionPointer;

    public HistoryManager(ReadOnlyEngagementList initialList) {
        super(initialList);
        versionPointer = 0;
        historyBook = new LinkedList<>();
        historyBook.add(new EngagementList(initialList));
    }

    public ReadOnlyEngagementList getAddressBook() {
        return historyBook.get(versionPointer);
    }

    /**
     * Reverts the list to its previous state.
     */
    public void undo() throws NullUndoableActionException {
        if (!isUndoable()) {
            throw new NullUndoableActionException();
        }
        versionPointer--;
        resetData(historyBook.get(versionPointer));
    }

    /**
     * Reverts the list to its previously undone state.
     */
    public void redo() throws NullRedoableActionException {
        if (!isRedoable()) {
            throw new NullRedoableActionException();
        }

        versionPointer++;
        resetData(historyBook.get(versionPointer));
    }

    public boolean isUndoable() {
        return versionPointer > 0;
    }

    public boolean isRedoable() {
        return versionPointer < historyBook.size() - 1;
    }

    private void clearUpToNow() {
        historyBook.subList(versionPointer + 1, historyBook.size()).clear();
    }

    /**
     * Saves the current state of appointmentList and discards previous undone changes.
     */
    public void saveState() {
        clearUpToNow();
        historyBook.add(new EngagementList(this));
        versionPointer++;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof HistoryManager)) {
            return false;
        }

        HistoryManager otherStatedAppointmentList = (HistoryManager) other;
        return super.equals(otherStatedAppointmentList)
                && versionPointer == otherStatedAppointmentList.versionPointer;
    }
}
