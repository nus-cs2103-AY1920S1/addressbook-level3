package seedu.mark.model.reminder;

import static seedu.mark.commons.util.CollectionUtil.requireAllNonNull;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.exceptions.BookmarkContainNoReminderException;
import seedu.mark.model.bookmark.exceptions.ExistReminderException;
import seedu.mark.model.bookmark.exceptions.ReminderNotFoundException;

import java.time.LocalDateTime;
import java.util.Comparator;

/**
 * Represents the association between bookmarks and reminders.
 */
public class ReminderAssociation {
    private static final Comparator<Reminder> comparator = (Reminder a, Reminder b) ->
            (a.getRemindTime().isBefore(b.getRemindTime())) ? -1 : 1;
    //TODO: One bookmark may has multiple reminder in next version.
    private ObservableMap<Bookmark, Reminder> association = FXCollections.observableHashMap();

    /**
     * Sets the reminder association with the given association.
     *
     * @param association the given association.
     */
    public void setAssociation(ObservableMap<Bookmark, Reminder> association) {
        this.association = association;
    }

    /**
     * Adds a reminder that opens the bookmark.
     *
     * @param bookmark the bookmark that is opened by the reminder.
     * @param reminder the reminder that is added.
     */
    public void addReminder(Bookmark bookmark, Reminder reminder) {
        requireAllNonNull(bookmark, reminder);

        if (association.containsKey(bookmark)) {
            throw new ExistReminderException();
        }

        association.put(bookmark, reminder);
    }

    /**
     * Deletes a specific reminder.
     *
     * @param reminder the reminder to delete.
     */
    public void deleteReminder(Reminder reminder) {
        requireAllNonNull(reminder);
        Bookmark bookmark = reminder.getBookmark();

        if (!association.containsKey(bookmark)) {
            throw new BookmarkContainNoReminderException();
        }

        if(!association.remove(bookmark, reminder)) {
            throw new ReminderNotFoundException();
        }
    }

    /**
     * Replaces the bookmark {@code targetReminder} in the map with {@code editedReminder}.
     * {@code targetReminder} must exist in the map.
     */
    public void setReminder(Reminder targetReminder, Reminder editedReminder) {
        requireAllNonNull(targetReminder, editedReminder);
        Bookmark bookmark = targetReminder.getBookmark();

        if (!association.containsKey(bookmark)) {
            throw new BookmarkContainNoReminderException();
        }

        if (!association.replace(bookmark, targetReminder, editedReminder)) {
            throw new ReminderNotFoundException();
        }
    }

    /**
     * Gets a list of reminders in ascending time order.
     *
     * @return a list of reminder sorted in time ascending order.
     */
    public ObservableList<Reminder> getReminderList() {
        ObservableList<Reminder> reminderList = FXCollections.observableArrayList();
        reminderList.addAll(association.values());
        reminderList.sort(comparator);
        return reminderList;
    }

    @Override
    public int hashCode() {
        return association.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReminderAssociation // instanceof handles nulls
                && association.equals(((ReminderAssociation) other).association));
    }
}
