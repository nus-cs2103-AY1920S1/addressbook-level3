package seedu.address.commons.stub;

import seedu.address.logic.UiManager;
import seedu.address.model.CalendarDate;

/**
 * Represents a stub for the UiManager that is used for testing purpose only.
 * @see UiManager
 */
public class UiManagerStub extends UiManager {

    @Override
    public void viewCalendar(CalendarDate calendarDate) {
        return;
    }

    @Override
    public void viewList() {
        return;
    }

    @Override
    public void viewLog() {
        return;
    }

    @Override
    public void viewDay(CalendarDate calendarDate) {
        return;
    }

    @Override
    public void viewWeek(CalendarDate calendarDate) {
        return;
    }

    @Override
    public void viewMonth(CalendarDate calendarDate) {
        return;
    }
}
