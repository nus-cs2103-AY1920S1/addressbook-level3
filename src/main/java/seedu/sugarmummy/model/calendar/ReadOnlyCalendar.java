package seedu.sugarmummy.model.calendar;

import javafx.collections.ObservableList;
import seedu.sugarmummy.model.ReadOnlyData;

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
