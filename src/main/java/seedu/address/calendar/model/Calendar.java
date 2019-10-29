package seedu.address.calendar.model;

import seedu.address.calendar.model.date.MonthOfYear;
import seedu.address.calendar.model.date.ViewOnlyMonth;
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
    private ViewOnlyMonth viewOnlyMonth;
    private boolean hasVisibleUpdates;
    private EventManager events;

    public Calendar() {
        java.util.Calendar currentDate = java.util.Calendar.getInstance();

        Year currentYear = new Year(currentDate.get(java.util.Calendar.YEAR));

        int currentUnformattedMonth = currentDate.get(java.util.Calendar.MONTH);
        MonthOfYear currentMonth = DateUtil.convertJavaMonth(currentUnformattedMonth);

        viewOnlyMonth = new ViewOnlyMonth(currentMonth, currentYear);
        hasVisibleUpdates = false;
        events = new EventManager();
    }

    public ViewOnlyMonth getMonth() {
        return ViewOnlyMonth.copy(viewOnlyMonth);
    }

    public void updateMonthShown(ViewOnlyMonth updatedViewOnlyMonth) {
        viewOnlyMonth = updatedViewOnlyMonth;
        hasVisibleUpdates = true;
    }

    // todo: pass in and call ViewOnlyMonth getInstance
    // todo: think about how to update for the first time

    /**
     * Checks whether there are any visible update that has to be shown to user.
     *
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
        events.add(event);
        return true;
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
