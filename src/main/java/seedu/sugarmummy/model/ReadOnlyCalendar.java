package seedu.sugarmummy.model;

import javafx.collections.ObservableList;
import seedu.sugarmummy.model.calendar.CalendarEntry;

/**
 * Unmodifiable view of a calendar
 */
public interface ReadOnlyCalendar extends ReadOnlyData {
    /**
     * Returns an unmodifiable view of the calendar entry list. This list will not contain any duplicate calendar
     * entries.
     */
    ObservableList<CalendarEntry> getCalendarEntryList();
}
