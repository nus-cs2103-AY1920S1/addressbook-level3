package seedu.address.model.reminders;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.beans.PropertyChangeListener;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Entry;
import seedu.address.model.person.exceptions.EntryNotFoundException;
import seedu.address.model.reminders.conditions.EntrySpecificCondition;


/**
 * A list of reminders that does not allow nulls.
 * Supports a minimal set of list operations.
 *
 */
public class ReminderList implements Iterable<Reminder> {
    private final ObservableList<Reminder> internalList = FXCollections.observableArrayList();
    private final ObservableList<Reminder> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);
    private int displayListSize = 3;

    /**
     * Determines how many reminders to display in reminders panel.
     */
    public void setDisplayListSize(int size) {
        this.displayListSize = size;
    }

    public int getDisplayListSize() {
        return displayListSize;
    }

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Reminder toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Reminder toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
        //internalList.sort(new sortByPriority());
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setReminder(Reminder target, Reminder editedReminder) {
        requireAllNonNull(target, editedReminder);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new EntryNotFoundException();
        }
        internalList.set(index, editedReminder);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Reminder toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new EntryNotFoundException();
        }
    }

    public void setEntries(ReminderList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setEntries(List<Reminder> entries) {
        requireAllNonNull(entries);
        internalList.setAll(entries);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Reminder> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * When an entry is removed, the corresponding Entry Specific Reminder must be removed if present.
     * @param entry
     */
    public void removeEntrySpecificReminder(Entry entry) {
        if (entry.getTracker().isPresent()) {
            EntrySpecificCondition condition = entry.getTracker().get();
            for (PropertyChangeListener listener: condition.getSupport().getPropertyChangeListeners()) {
                internalList.remove(listener);
            }
        }
    }

    @Override
    public Iterator<Reminder> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReminderList // instanceof handles nulls
                && internalList.equals(((ReminderList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
