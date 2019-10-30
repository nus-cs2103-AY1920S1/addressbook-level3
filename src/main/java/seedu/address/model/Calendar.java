package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.calendar.CalendarEntry;
import seedu.address.model.calendar.Reminder;
import seedu.address.model.calendar.Scheduler;
import seedu.address.model.calendar.UniqueCalendarEntryList;

/**
 * Wraps all data at the calendar level Duplicates are not allowed
 */
public class Calendar implements ReadOnlyCalendar {
    private final UniqueCalendarEntryList calendarEntries;
    private final UniqueCalendarEntryList pastReminders;
    private final Scheduler scheduler;

    {
        calendarEntries = new UniqueCalendarEntryList();
        pastReminders = new UniqueCalendarEntryList();
        scheduler = new Scheduler();
    }

    public Calendar() {
    }

    /**
     * Creates an calendar using the CalendarEntries in the {@code toBeCopied}
     */
    public Calendar(ReadOnlyCalendar toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the calendar list with {@code calendarEntries}. {@code calendarEntries} must not contain
     * duplicate calendar entries.
     */
    public void setCalendarEntries(List<CalendarEntry> calendarEntries) {
        this.calendarEntries.setCalendarEntries(calendarEntries);
    }

    /**
     * Resets the existing data of this {@code Calendar} with {@code newData}.
     */
    public void resetData(ReadOnlyCalendar newData) {
        requireNonNull(newData);

        setCalendarEntries(newData.getCalendarEntryList());
    }

    //// person-level operations

    /**
     * Returns true if a calendar entry with the same identity as {@code calendarEntry} exists in the calendar.
     */
    public boolean hasCalendarEntry(CalendarEntry calendarEntry) {
        requireNonNull(calendarEntry);
        return calendarEntries.contains(calendarEntry);
    }

    /**
     * Adds a calendar to the calendar. The calendar entry must not already exist in the calendar.
     */
    public void addCalendarEntry(CalendarEntry calendarEntry) {
        calendarEntries.add(calendarEntry);
    }

    /**
     * Adds a reminder to the past reminder list. The reminder must not already exist in the past reminder list.
     */
    public void addPastReminders(List<Reminder> reminders) {
        pastReminders.addAll(reminders);
    }

    /**
     * Schedules upcoming reminders.
     */
    public void schedule() {
        scheduler.schedule(this);
    }

    /**
     * Stops all upcoming reminders.
     */
    public void stopAllReminders() {
        scheduler.stopAll();
    }

    /**
     * Replaces the given calendar entry {@code target} in the list with {@code editedCalendarEntry}. {@code target}
     * must exist in the calendar. The calendar entry identity of {@code editedCalendar} must not be the same as another
     * existing calendar entry in the calendar.
     */
    public void setCalendarEntry(CalendarEntry target, CalendarEntry editedCalendarEntry) {
        requireNonNull(editedCalendarEntry);

        calendarEntries.setCalendarEntry(target, editedCalendarEntry);
    }

    /**
     * Removes {@code key} from this {@code Calendar}. {@code key} must exist in the calendar.
     */
    public void removeCalendarEntry(CalendarEntry key) {
        calendarEntries.remove(key);
    }

    public ObservableList<CalendarEntry> getPastReminderList() {
        return pastReminders.asUnmodifiableObservableList();
    }

    @Override
    public String toString() {
        return calendarEntries.asUnmodifiableObservableList().size() + " persons";
    }

    @Override
    public ObservableList<CalendarEntry> getCalendarEntryList() {
        return calendarEntries.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Calendar // instanceof handles nulls
                && calendarEntries.equals(((Calendar) other).calendarEntries));
    }

    @Override
    public int hashCode() {
        return calendarEntries.hashCode();
    }
}
