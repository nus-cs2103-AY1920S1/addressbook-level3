package seedu.sugarmummy.model.calendar.exceptions;

/**
 * Signals that the operation will result in duplicate CalendarEntries.
 */
public class DuplicateCalendarEntryException extends RuntimeException {
    public DuplicateCalendarEntryException() {
        super("Operation would result in duplicate calendar entries");
    }
}
