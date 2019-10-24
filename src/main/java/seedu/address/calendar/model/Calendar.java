package seedu.address.calendar.model;

import seedu.address.calendar.model.date.Year;
import seedu.address.calendar.model.event.Event;
import seedu.address.calendar.model.util.DateUtil;
import seedu.address.calendar.model.date.MonthOfYear;

import java.util.ArrayList;
import java.util.List;

public class Calendar {
    private Month monthShown;
    private boolean hasVisibleUpdates;
    private List<Event> events;

    public Calendar() {
        java.util.Calendar currentDate = java.util.Calendar.getInstance();

        Year currentYear = new Year(currentDate.get(java.util.Calendar.YEAR));

        int currentUnformattedMonth = currentDate.get(java.util.Calendar.MONTH);
        MonthOfYear currentMonth = DateUtil.convertJavaMonth(currentUnformattedMonth);

        monthShown = new Month(currentMonth, currentYear);
        hasVisibleUpdates = false;
        events = new ArrayList<>(); // todo: update this such that it is linked to storage
    }

    public Month getMonth() {
        return Month.copy(monthShown);
    }

    public void updateMonthShown(Month updatedMonth) {
        monthShown = updatedMonth;
        hasVisibleUpdates = true;
    }

    /**
     * Checks whether there are any visible update that has to be shown to user.
     * @return {@code true} if and only if there are visible updates that can be shown to user
     */
    public boolean hasVisibleUpdates() {
        return hasVisibleUpdates;
    }

    /**
     * Marks visible update as complete, i.e. latest visible update has been shown to user.
     */
    public void completeVisibleUpdates() {
        hasVisibleUpdates = false;
    }

    /**
     * Adds an event to the calendar
     */
    public void addEvent(Event event) {
        events.add(event);
    }

    // todo: extract the following and implement it using model manager
    public ReadOnlyCalendar getCalendar() {
        List<Event> eventsCopy = List.copyOf(events);
        return new ReadOnlyCalendar(eventsCopy);
    }

    // todo: update
    public void updateCalendar(ReadOnlyCalendar readOnlyCalendar) {
        events.clear();
        readOnlyCalendar.getEventList()
                .stream()
                .forEach(e -> events.add(e));
    }
}
