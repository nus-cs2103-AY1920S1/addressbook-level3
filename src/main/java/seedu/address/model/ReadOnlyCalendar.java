package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.calendar.CalendarEntry;

/**
 * Unmodifiable view of a calendar
 */
public interface ReadOnlyCalendar {
    /**
     * Returns an unmodifiable view of the calendar entry list. This list will not contain any
     * duplicate calendar entries.
     */
    ObservableList<CalendarEntry> getCalendarEntryList();
}
