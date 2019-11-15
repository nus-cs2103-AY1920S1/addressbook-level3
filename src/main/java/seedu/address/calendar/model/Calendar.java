package seedu.address.calendar.model;

import java.nio.file.NoSuchFileException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.calendar.model.date.Date;
import seedu.address.calendar.model.date.MonthOfYear;
import seedu.address.calendar.model.date.ViewOnlyMonth;
import seedu.address.calendar.model.date.Year;
import seedu.address.calendar.model.event.Event;
import seedu.address.calendar.model.event.EventManager;
import seedu.address.calendar.model.event.EventQuery;
import seedu.address.calendar.model.event.exceptions.ClashException;
import seedu.address.calendar.model.event.exceptions.DuplicateEventException;
import seedu.address.calendar.model.util.CalendarStatistics;
import seedu.address.calendar.model.util.DateUtil;
import seedu.address.calendar.model.util.exceptions.NoVacationException;

/**
 * Represents a calendar. The calendar is aware of what has to be shown to the user graphically and has access to
 * manipulating the user's events.
 */
public class Calendar {
    private ViewOnlyMonth viewOnlyMonth;
    private boolean hasVisibleUpdates;
    private EventManager events;

    /**
     * Creates a {@code Calendar}.
     */
    public Calendar() {
        java.util.Calendar currentDate = java.util.Calendar.getInstance();

        Year currentYear = new Year(currentDate.get(java.util.Calendar.YEAR));

        int currentUnformattedMonth = currentDate.get(java.util.Calendar.MONTH);
        MonthOfYear currentMonth = DateUtil.convertJavaMonth(currentUnformattedMonth);

        viewOnlyMonth = new ViewOnlyMonth(currentMonth, currentYear);
        hasVisibleUpdates = false;
        events = new EventManager();
    }

    /**
     * Gets information about the month that is currently being shown.
     * @return
     */
    public ViewOnlyMonth getMonth() {
        return ViewOnlyMonth.copy(viewOnlyMonth);
    }

    /**
     * Adds an event to the calendar and switches the month view to that of the event's start date.
     *
     * @param event The event to add to {@code this}
     * @return {@code true} if the operation is successful
     * @throws DuplicateEventException if the specified already exists
     * @throws ClashException if the operation could lead to conflicting schedules
     */
    public boolean addEvent(Event event) throws DuplicateEventException, ClashException {
        events.add(event);
        updateMonthView(event);
        return true;
    }

    /**
     * Adds an event to the calendar and switches the month view to that of the event's start date.
     * This operation ignores any potential conflicts in schedule.
     *
     * @param event The event to add to {@code this}
     * @return {@code true} if the operation is successful
     * @throws DuplicateEventException if the specified already exists
     */
    public boolean addIgnoreClash(Event event) throws DuplicateEventException {
        events.addIgnoreClash(event);
        updateMonthView(event);
        return true;
    }

    /**
     * Deletes an event from {@code this}.
     *
     * @param event The specified event to be deleted
     * @return {@code true} if the operation is successful
     * @throws NoSuchElementException if the specified event cannot be found in {@code this}
     */
    public boolean deleteEvent(Event event) throws NoSuchElementException {
        events.remove(event);
        updateMonthView(event);
        return true;
    }

    /**
     * Lists all events in {@code this}.
     *
     * @return A {@code String} which contains information about all events in {@code this}
     */
    public String listAll() {
        return events.listAllAsString()
                .stream()
                .reduce("", (prev, curr) -> prev + "\n" + curr)
                .trim();
    }

    /**
     * Lists all events that happen during the specified event query.
     *
     * @param eventQuery The specified event query
     * @return A {@code String} which contains information about all events that happen during {@code eventQuery}
     */
    public String list(EventQuery eventQuery) {
        updateMonthView(eventQuery);
        return events.listRelevantAsString(eventQuery)
                .stream()
                .reduce("", (prev, curr) -> prev + "\n" + curr)
                .trim();
    }

    public Stream<Event> getEventsAtSpecificTime(EventQuery eventQuery) {
        return events.getEventsAtSpecificTime(eventQuery);
    }

    /**
     * Checks if the user is available at the specified period of time.
     *
     * @param eventQuery The specified period of time
     * @return {@code true} if the user is available at the specified period of time
     */
    public boolean isAvailable(EventQuery eventQuery) {
        boolean isAvailable = events.isAvailable(eventQuery);
        updateMonthView(eventQuery);
        return isAvailable;
    }

    /**
     * Suggests user periods of times when he/she is free.
     *
     * @param eventQuery The period of time of interest
     * @return Suggestions for the user
     */
    public String suggest(EventQuery eventQuery) {
        String suggestions = events.suggest(eventQuery);
        updateMonthView(eventQuery);
        return suggestions;
    }

    /**
     * Suggests user periods of times when he/she is free and only returns those that meets the minimum time period.
     *
     * @param eventQuery The period of time of interest
     * @param minPeriod The minimum period of time
     * @return Suggestions for the user
     */
    public String suggest(EventQuery eventQuery, int minPeriod) {
        String suggestions = events.suggest(eventQuery, minPeriod);
        updateMonthView(eventQuery);
        return suggestions;
    }

    /**
     * Gets a read-only calendar.
     *
     * @return A read-only calendar
     */
    public ReadOnlyCalendar getCalendar() {
        List<Event> eventsCopy = events.asList();
        return new ReadOnlyCalendar(eventsCopy);
    }

    /**
     * Updates calendar using a read-only calendar.
     *
     * @param readOnlyCalendar A read-only calendar
     * @throws NoSuchFileException If the file cannot be found
     */
    public void updateCalendar(Optional<ReadOnlyCalendar> readOnlyCalendar) throws NoSuchFileException {
        try {
            List<Event> eventList = readOnlyCalendar.get().getEventList();
            events.clear();
            for (Event event : eventList) {
                addEventFromReadable(event);
            }
        } catch (DuplicateEventException e) {
            throw e;
        } catch (NoSuchElementException e) {
            throw new NoSuchFileException("Data file not found. Will be starting with a sample AddressBook");
        }

        // get current year/month info
        java.util.Calendar currentDate = java.util.Calendar.getInstance();
        Year currentYear = new Year(currentDate.get(java.util.Calendar.YEAR));
        int currentUnformattedMonth = currentDate.get(java.util.Calendar.MONTH);
        MonthOfYear currentMonth = DateUtil.convertJavaMonth(currentUnformattedMonth);

        // update viewable month
        updateMonthView(currentMonth, currentYear);
    }

    /**
     * Adds event from the read-only calendar.
     *
     * @param event The event to be added
     * @throws DuplicateEventException If there are duplicated events detected
     * @throws NoSuchElementException If there is no such element
     */
    private void addEventFromReadable(Event event) throws DuplicateEventException, NoSuchElementException {
        try {
            events.add(event);
        } catch (ClashException e) {
            events.addIgnoreClash(event);
        }
    }

    private void updateMonthView(Event event) {
        EventQuery eventQuery = event.asEventQuery();
        updateMonthView(eventQuery);
    }

    private void updateMonthView(EventQuery eventQuery) {
        ViewOnlyMonth updatedViewOnlyMonth = ViewOnlyMonth.getInstance(events, eventQuery);
        updateMonthView(updatedViewOnlyMonth);
    }

    /**
     * Updates month view with the specified month and year.
     *
     * @param month The specified month
     * @param year The specified year
     */
    public void updateMonthView(MonthOfYear month, Year year) {
        Date firstDateOfMonth = DateUtil.getFirstDateInMonth(month, year);
        Date lastDateOfMonth = DateUtil.getLastDateInMonth(month, year);

        EventQuery monthQuery = new EventQuery(firstDateOfMonth, lastDateOfMonth);
        ViewOnlyMonth updatedViewOnlyMonth = ViewOnlyMonth.getInstance(events, monthQuery);
        updateMonthView(updatedViewOnlyMonth);
    }

    private void updateMonthView(ViewOnlyMonth updatedViewOnlyMonth) {
        viewOnlyMonth = updatedViewOnlyMonth;
        hasVisibleUpdates = true;
    }

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
     * Gets the number of days of vacation (i.e. school breaks and holidays)
     *
     * @return Number of days of vacation (i.e. school breaks and holidays)
     */
    private long getNumDaysVacation() {
        return events.getNumDaysVacation();
    }

    /**
     * Gets the number of days spent on trips
     *
     * @return Number of days spent on trips
     */
    private long getNumDaysTrip() {
        return events.getNumDaysTrip();
    }

    /**
     * Gets the number of trips.
     *
     * @return Absolute number of trips
     */
    private long getNumTrip() {
        return events.getNumTrip();
    }

    /**
     * Gets the percentage of vacation that is spent on trips.
     *
     * @return Percentage of vacation that is spent on trips
     * @throws NoVacationException If there is no vacation
     */
    private double getPercentageTrip() throws NoVacationException {
        return events.getPercentageTrip();
    }

    public CalendarStatistics getStatistics() {
        return new CalendarStatisticsManager();
    }

    /**
     * Creates a statistics object that contains statistics of {@code this} calendar.
     */
    private class CalendarStatisticsManager implements CalendarStatistics {
        @Override
        public long getNumDaysVacation() {
            return Calendar.this.getNumDaysVacation();
        }

        @Override
        public long getNumDaysTrip() {
            return Calendar.this.getNumDaysTrip();
        }

        @Override
        public long getNumTrip() {
            return Calendar.this.getNumTrip();
        }

        @Override
        public double getPercentageTrip() throws NoVacationException {
            return Calendar.this.getPercentageTrip();
        }

    }
}
