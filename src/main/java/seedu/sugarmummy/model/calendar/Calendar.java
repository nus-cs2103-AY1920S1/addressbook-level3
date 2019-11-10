package seedu.sugarmummy.model.calendar;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.sugarmummy.model.time.Today;

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

    /**
     * Returns true if a calendar entry with the same identity as {@code calendarEntry} exists in the calendar.
     */
    public boolean hasCalendarEntry(CalendarEntry calendarEntry) {
        requireNonNull(calendarEntry);
        return calendarEntries.contains(calendarEntry);
    }

    /**
     * Returns true if any calendar entry in the calendar covers {@code calendarEntry}.
     */
    public boolean coversCalendarEntry(CalendarEntry calendarEntry) {
        requireNonNull(calendarEntry);
        return calendarEntries.covers(calendarEntry);
    }

    /**
     * Returns all calendar entries in the calendar that cover {@code calendarEntry}.
     */
    public CalendarEntry getCalendarEntryCovers(CalendarEntry calendarEntry) {
        requireNonNull(calendarEntry);
        return calendarEntries.getCalendarEntryCovers(calendarEntry);
    }

    /**
     * Returns true if any calendar entry in the calendar is covered by {@code calendarEntry}.
     */
    public boolean isAnyCoveredByCalendarEntry(CalendarEntry calendarEntry) {
        requireNonNull(calendarEntry);
        return calendarEntries.isAnyCoveredBy(calendarEntry);
    }

    /**
     * Returns all calendar entries in the calendar that are covered by {@code calendarEntry}.
     */
    public ObservableList<CalendarEntry> getCalendarEntriesCoveredBy(CalendarEntry calendarEntry) {
        requireNonNull(calendarEntry);
        return calendarEntries.getCalendarEntriesCoveredBy(calendarEntry);
    }

    /**
     * Returns true if any calendar entry in the calendar overlaps {@code calendarEntry}.
     */
    public boolean overlapsCalendarEntry(CalendarEntry calendarEntry) {
        requireNonNull(calendarEntry);
        return calendarEntries.overlaps(calendarEntry);
    }

    /**
     * Returns all calendar entries in the calendar that overlap {@code calendarEntry}.
     */
    public ObservableList<CalendarEntry> getCalendarEntryOverlaps(CalendarEntry calendarEntry) {
        requireNonNull(calendarEntry);
        return calendarEntries.getCalendarEntryOverlaps(calendarEntry);
    }

    /**
     * Returns true if any calendar entry in the calendar and {@code calendarEntry} conflict.
     */
    public boolean conflictsCalendarEntry(CalendarEntry calendarEntry) {
        requireNonNull(calendarEntry);
        return calendarEntries.conflicts(calendarEntry);
    }

    /**
     * Returns all calendar entries in the calendar that are conflicted with {@code calendarEntry}.
     */
    public ObservableList<CalendarEntry> getCalendarEntriesConflicts(CalendarEntry calendarEntry) {
        requireNonNull(calendarEntry);
        return calendarEntries.getCalendarEntryConflicts(calendarEntry);
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
        pastReminders.addAll(reminders.stream()
                .map(reminder -> reminder.getOneTimeReminderOn(scheduler.getTodayDate()))
                .collect(Collectors.toList()));
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
     * Returns a Today object represents the date of today.
     */
    public Today getToday() {
        return scheduler.getToday();
    }

    /**
     * Returns the date time when the app starts.
     */
    public LocalDateTime getAppStartingDateTime() {
        return scheduler.getAppStartingDateTime();
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

    /**
     * Removes {@code keys} from this {@code Calendar}. {@code keys} must exist in the calendar.
     */
    public void removeAllCalendarEntries(List<CalendarEntry> keys) {
        calendarEntries.removeAll(keys);
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
