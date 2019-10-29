package seedu.address.calendar.model;

import seedu.address.calendar.model.date.MonthOfYear;
import seedu.address.calendar.model.date.Year;
import seedu.address.calendar.model.event.Event;
import seedu.address.calendar.model.event.EventManager;
import seedu.address.calendar.model.event.EventQuery;
import seedu.address.calendar.model.event.exceptions.ClashException;
import seedu.address.calendar.model.event.exceptions.DuplicateEventException;
import seedu.address.calendar.model.util.DateUtil;

import java.util.List;
import java.util.NoSuchElementException;

public class Calendar {
    private Month monthShown;
    private boolean hasVisibleUpdates;
    private EventManager events;

    public Calendar() {
        java.util.Calendar currentDate = java.util.Calendar.getInstance();

        Year currentYear = new Year(currentDate.get(java.util.Calendar.YEAR));

        int currentUnformattedMonth = currentDate.get(java.util.Calendar.MONTH);
        MonthOfYear currentMonth = DateUtil.convertJavaMonth(currentUnformattedMonth);

        monthShown = new Month(currentMonth, currentYear);
        hasVisibleUpdates = false;
        events = new EventManager(); // todo: update this such that it is linked to storage
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
    public boolean addEvent(Event event) throws DuplicateEventException, ClashException {
        return events.add(event);
    }

    public boolean addIgnoreClash(Event event) throws DuplicateEventException {
        return events.addIgnoreClash(event);
    }

    /**
     * Deletes an event from the calendar
     */
    public boolean deleteEvent(Event event) throws NoSuchElementException {
        return events.remove(event);
    }

    public boolean isAvailable(EventQuery eventQuery) {
        return events.isAvailable(eventQuery);
    }

    public String suggest(EventQuery eventQuery) {
        return events.suggest(eventQuery);
    }

    public String suggest(EventQuery eventQuery, int minPeriod) {
        return events.suggest(eventQuery, minPeriod);
    }

    // todo: extract the following and implement it using model manager
    public ReadOnlyCalendar getCalendar() {
        List<Event> eventsCopy = events.asList();
        return new ReadOnlyCalendar(eventsCopy);
    }

    // todo: update
    public void updateCalendar(ReadOnlyCalendar readOnlyCalendar) {
        events.clear();
        List<Event> eventList = readOnlyCalendar.getEventList();
        try {
            for (Event event : eventList) {
                events.add(event);
            }
        } catch (DuplicateEventException e) {
            throw e;
        } catch (ClashException e) {
            // do nothing
        }
    }
}
