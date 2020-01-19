package seedu.mark.model.reminder;

import static seedu.mark.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.exceptions.BookmarkContainNoReminderException;
import seedu.mark.model.bookmark.exceptions.ExistReminderException;
import seedu.mark.model.bookmark.exceptions.ReminderNotFoundException;

/**
 * Represents the association between bookmarks and reminders.
 */
public class ReminderAssociation {
    private static final Comparator<Reminder> comparator = (Reminder a, Reminder b) -> {
        return a.getRemindTime().isBefore(b.getRemindTime()) ? -1 : 1;
    };
    //TODO: One bookmark may has multiple reminder in next version.
    private ObservableMap<Bookmark, Reminder> association = FXCollections.observableHashMap();
    private ObservableMap<Reminder, Bookmark> reminderMap = FXCollections.observableHashMap();

    /**
     * Sets the reminder association with the given association.
     *
     * @param association the given association.
     */
    public void setAssociation(ObservableMap<Bookmark, Reminder> association) {
        this.association.clear();
        this.reminderMap.clear();
        for (Bookmark bookmark : association.keySet()) {
            this.association.put(bookmark, association.get(bookmark));
            this.reminderMap.put(association.get(bookmark), bookmark);
        }
    }

    /**
     * Gets the reminder association.
     *
     */
    public ObservableMap<Bookmark, Reminder> getAssociation() {
        return association;
    }

    /**
     * Adds a reminder that opens the bookmark.
     *
     * @param bookmark The bookmark that the reminder opens.
     * @param reminder The reminder that opens the bookmark.
     */
    public void addReminder(Bookmark bookmark, Reminder reminder) {
        requireAllNonNull(bookmark, reminder);

        if (association.containsKey(bookmark)) {
            throw new ExistReminderException();
        }

        association.put(bookmark, reminder);
        reminderMap.put(reminder, bookmark);
    }

    /**
     * Deletes a specific reminder.
     *
     * @param reminder the reminder to delete.
     */
    public void deleteReminder(Reminder reminder) {
        requireAllNonNull(reminder);
        if (!reminderMap.containsKey(reminder)) {
            throw new ReminderNotFoundException();
        }

        Bookmark bookmark = reminderMap.get(reminder);

        if (!association.containsKey(bookmark)) {
            throw new BookmarkContainNoReminderException();
        }

        if (!association.remove(bookmark, reminder)) {
            throw new ReminderNotFoundException();
        }
        reminderMap.remove(reminder);
    }

    /**
     * Finds the bookmark for a specific reminder.
     *
     * @param reminder the reminder of the bookmark.
     * @return the bookmark of the reminder.
     */
    public Bookmark getBookmarkFromReminder(Reminder reminder) {
        if (!reminderMap.containsKey(reminder)) {
            throw new ReminderNotFoundException();
        }
        return reminderMap.get(reminder);
    }

    /**
     * Replaces the bookmark's reminder {@code targetReminder} in the map with {@code editedReminder}.
     * {@code targetReminder} must exist in the map.
     */
    public void setReminder(Reminder targetReminder, Reminder editedReminder) {
        requireAllNonNull(targetReminder, editedReminder);
        if (!reminderMap.containsKey(targetReminder)) {
            throw new ReminderNotFoundException();
        }

        Bookmark bookmark = reminderMap.get(targetReminder);

        if (!association.containsKey(bookmark)) {
            throw new BookmarkContainNoReminderException();
        }

        if (!association.replace(bookmark, targetReminder, editedReminder)) {
            throw new ReminderNotFoundException();
        }
        reminderMap.remove(targetReminder);
        reminderMap.put(editedReminder, bookmark);
    }

    /**
     * Delete a specified bookmark.
     *
     * @param bookmark the bookmark to delete.
     */
    public void removeBookmark(Bookmark bookmark) {
        if (association.containsKey(bookmark)) {
            Reminder reminder = association.get(bookmark);
            association.remove(bookmark);
            reminderMap.remove(reminder);
        }
    }

    /**
     * Edit a specified bookmark.
     *
     * @param targetBookmark the bookmark to be edited.
     * @param newBookmark the edited bookmark.
     */
    public void editBookmark(Bookmark targetBookmark, Bookmark newBookmark) {
        if (association.containsKey(targetBookmark)) {
            Reminder reminder = association.get(targetBookmark);
            Reminder newReminder =
                    new Reminder(newBookmark.getUrl(), reminder.getRemindTime(), reminder.getNote());
            deleteReminder(reminder);
            addReminder(newBookmark, newReminder);
        }
    }

    /**
     * Gets a list of reminders in ascending time order.
     *
     * @return a list of reminder sorted in time ascending order.
     */
    public ObservableList<Reminder> getReminderList() {
        ObservableList<Reminder> reminderList = FXCollections.observableArrayList();
        if (!association.isEmpty()) {
            reminderList.addAll(association.values());
            reminderList.sort(comparator);
        }
        return reminderList;
    }

    /**
     * Checks if the bookmark already has reminder.
     *
     * @param bookmark the bookmark to check.
     * @return whether the bookmark already has a reminder.
     */
    public boolean isBookmarkHasReminder(Bookmark bookmark) {
        return association.containsKey(bookmark);
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
