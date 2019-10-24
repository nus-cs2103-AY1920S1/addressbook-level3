package seedu.address.model;

import static seedu.address.commons.util.CollectionUtil.collectionToString;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.MutatorCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;

/**
 * Represents the history of the application. Each data state change is stored as a record containing the
 * {@link MutatorCommand} that caused the change and the {@link AddressBook} state before the command execution.
 * All recorded states are deep copies decoupled from their original references.
 */
public class HistoryManager {
    private final int maxSize;
    private List<HistoryRecord> history = new ArrayList<>(); // Record of command and state BEFORE its execution
    private Deque<HistoryRecord> redoStack = new LinkedList<>(); // Record of command and state AFTER its execution

    /**
     * Initialises a HistoryManager with the specified maximum size of history records to keep track of.
     *
     * @param maxSize the maximum number of records in the history
     */
    public HistoryManager(int maxSize) {
        assert maxSize >= 0;
        this.maxSize = maxSize;
    }

    //// Setter methods

    /**
     * Records the specified command and state in the history. The {@code addressBook} represents the state before the
     * {@code command} was executed.
     *
     * @param command the command that caused the change in state
     * @param addressBook the state before the command was executed
     */
    public void pushRecord(MutatorCommand command, AddressBook addressBook) {
        if (command instanceof UndoCommand || command instanceof RedoCommand) {
            return;
        }
        pushToHistory(command, addressBook);
        redoStack.clear(); // Invalidate redo stack to avoid branching
    }

    /**
     * Records the specified command and state in the history. This is different from
     * {@link #pushRecord(MutatorCommand, AddressBook)} in that the {@code redoStack} is not cleared to facilitate
     * pushing {@link #popRedo(AddressBook)}ed records back into the history while maintaining the redo stack.
     */
    private void pushToHistory(MutatorCommand command, AddressBook addressBook) {
        if (history.size() == maxSize) {
            history.remove(0);
        }
        history.add(new HistoryRecord(command, addressBook));
    }

    /**
     * Removes the most recent record from the history and returns it as an {@code Optional}, or an empty
     * {@code Optional} if there are no records in the history. Records the undo with the specified
     * {@code} currentAddressBook so it can be undone.
     *
     * @param currentAddressBook the current state of the {@code AddressBook} model, stored for redo purposes
     * @return an {@code Optional} describing the most recent record in the history, or an empty {@code Optional} if
     *         the history is empty
     */
    public Optional<HistoryRecord> popRecord(AddressBook currentAddressBook) {
        if (history.isEmpty()) {
            return Optional.empty();
        } else {
            redoStack.push(new HistoryRecord(getLastRecord().get().getCommand(), currentAddressBook));
            return Optional.of(history.remove(history.size() - 1));
        }
    }

    /**
     * Reverts history to the state just before the specified record.
     *
     * @param record the record to revert to
     * @param currentAddressBook the current state of the {@code AddressBook} model, stored for redo purposes
     * @return list of records popped with the first popped record at index 0
     * @throws NoSuchElementException if the specified record does not exist in the history
     */
    public List<HistoryRecord> popRecordsTo(HistoryRecord record, AddressBook currentAddressBook)
            throws NoSuchElementException {
        int index = history.indexOf(record);
        if (index == -1) { // record not found in history
            throw new NoSuchElementException("Cannot pop to specified record: record not found in history");
        }

        List<HistoryRecord> poppedRecords = new ArrayList<>();
        HistoryRecord curRecord = null;
        AddressBook undoneAddressBook = currentAddressBook;
        while (curRecord != record) {
            curRecord = popRecord(undoneAddressBook).get();
            poppedRecords.add(curRecord);
            if (!history.isEmpty()) {
                undoneAddressBook = (AddressBook) getLastRecord().get().getReadOnlyAddressBook();
            }
        }
        return poppedRecords;
    }

    /**
     * If the previous command was an undo, reverts it and returns a {@code HistoryRecord} containing the
     * {@code AddressBook} state after the undone command was executed. Otherwise, returns an empty {@code Optional}.
     *
     * @param currentAddressBook the current state of the {@code AddressBook} model, stored for undo purposes
     * @return an {@code Optional} describing the redone record, or an empty {@code Optional} if there are no undos to
     *         redo
     */
    public Optional<HistoryRecord> popRedo(AddressBook currentAddressBook) {
        if (redoStack.isEmpty()) {
            return Optional.empty();
        }
        HistoryRecord redoRecord = redoStack.pop();
        pushToHistory(redoRecord.getCommand(), currentAddressBook); // Save state before redone command
        return Optional.of(redoRecord);
    }

    //// Accessor methods

    /** Returns an unmodifiable view of the history */
    public ObservableList<HistoryRecord> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(FXCollections.observableList(history));
    }

    /**
     * Returns the number of records in the history
     */
    public int size() {
        return history.size();
    }

    private Optional<HistoryRecord> getLastRecord() {
        return history.size() >= 1 ? Optional.of(history.get(history.size() - 1)) : Optional.empty();
    }

    @Override
    public String toString() {
        return collectionToString(history);
    }
}
