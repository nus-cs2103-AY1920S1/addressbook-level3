package seedu.address.model;

import static seedu.address.commons.util.CollectionUtil.collectionToString;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.MutatorCommand;

/**
 * Represents the history of the application. Each data state change is stored as a record containing the
 * {@link MutatorCommand} that caused the change and the {@link AddressBook} state before the command execution.
 * All recorded states are deep copies decoupled from their original references.
 */
public class HistoryManager {
    private final int maxSize;
    private List<HistoryRecord> history = new ArrayList<>();

    /**
     * Initialises a HistoryManager with the specified maximum size of history records to keep track of.
     *
     * @param maxSize the maximum number of records in the history
     */
    public HistoryManager(int maxSize) {
        assert maxSize >= 0;
        this.maxSize = maxSize;
    }

    /**
     * Records the specified command and state in the history. The {@code addressBook} represents the state before the
     * {@code command} was executed.
     *
     * @param command the command that caused the change in state
     * @param addressBook the state before the command was executed
     */
    public void pushRecord(MutatorCommand command, AddressBook addressBook) {
        if (history.size() == maxSize) {
            history.remove(0);
        }
        history.add(new HistoryRecord(command, addressBook));
    }

    /**
     * Removes the most recent record from the history and returns it as an {@code Optional}, or an empty
     * {@code Optional} if there are no records in the history.
     *
     * @return an {@code Optional} describing the most recent record in the history, or an empty {@code Optional} if
     *         the history is empty
     */
    public Optional<HistoryRecord> popRecord() {
        return history.isEmpty() ? Optional.empty() : Optional.of(history.remove(history.size() - 1));
    }

    /**
     * Reverts history to the state just before the specified record.
     *
     * @param record the record to revert to
     * @throws NoSuchElementException if the specified record does not exist in the history
     */
    public void popRecordsTo(HistoryRecord record) throws NoSuchElementException {
        int index = history.indexOf(record);
        if (index == -1) {
            throw new NoSuchElementException("Cannot pop to specified record: record not found in history");
        }
        HistoryRecord curRecord = null;
        while (curRecord != record) {
            curRecord = popRecord().get();
        }
    }

    /** Returns an unmodifiable view of the history */
    public ObservableList<HistoryRecord> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(FXCollections.observableList(history));
    }

    private Optional<HistoryRecord> getLastRecord() {
        return history.size() >= 1 ? Optional.of(history.get(history.size() - 1)) : Optional.empty();
    }

    @Override
    public String toString() {
        return collectionToString(history);
    }
}
