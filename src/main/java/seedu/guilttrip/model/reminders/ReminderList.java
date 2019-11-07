package seedu.guilttrip.model.reminders;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.commons.util.CollectionUtil.requireAllNonNull;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.guilttrip.model.entry.Entry;
import seedu.guilttrip.model.entry.exceptions.EntryNotFoundException;
import seedu.guilttrip.model.reminders.conditions.EntrySpecificCondition;
import seedu.guilttrip.model.reminders.messages.Message;
import seedu.guilttrip.model.reminders.messages.Notification;
import seedu.guilttrip.ui.UiManager;


/**
 * A list of reminders that does not allow nulls.
 * Supports a minimal set of list operations.
 *
 */
public class ReminderList implements Iterable<Reminder>, PropertyChangeListener {
    private final ObservableList<Reminder> internalList = FXCollections.observableArrayList();
    private final ObservableList<Reminder> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);
    private final ObservableList<Notification> notificationList = FXCollections.observableArrayList();
    private final ObservableList<Notification> internalNotificationList =
            FXCollections.unmodifiableObservableList(notificationList);

    private Reminder reminderSelected;

    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    public Reminder getReminderSelected() {
        return reminderSelected;
    }

    public void setReminderSelected(Reminder reminderSelected) {
        this.reminderSelected = reminderSelected;
    }

    public void linkToUi(UiManager uiManager) {
        support.addPropertyChangeListener(uiManager);
    }

    /**
     * Returns true if the list contains an equivalent entry as the given argument.
     */
    public boolean contains(Reminder toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a entry to the list.
     * The entry must not already exist in the list.
     */
    public void add(GeneralReminder toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
        //internalList.sort(new sortByPriority());
    }

    /**
     * Replaces the entry {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The entry identity of {@code editedPerson} must not be the same as another existing entry in the list.
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
     * Removes the equivalent entry from the list.
     * The entry must exist in the list.
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
     * Returns the notification list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Notification> asUnmodifiableNotificationList() {
        return internalNotificationList;
    }

    /**
     * When an entry is removed, the corresponding Entry Specific GeneralReminder must be removed if present.
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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("statusChange")) {
            Reminder reminder = (Reminder) evt.getNewValue();
            if (reminder.willDisplayPopUp()) {
                Message message = reminder.getMessage();
                support.firePropertyChange("NewReminderMessage", null, message);
            }
            if (reminder instanceof GeneralReminder) {
                reminder.reset();
            }
            notificationList.add(reminder.genNotification());
        }
    }
}
