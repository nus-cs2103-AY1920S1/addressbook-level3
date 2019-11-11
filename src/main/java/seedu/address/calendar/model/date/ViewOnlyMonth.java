package seedu.address.calendar.model.date;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import seedu.address.calendar.model.event.Event;
import seedu.address.calendar.model.event.EventQuery;
import seedu.address.calendar.model.event.EventType;
import seedu.address.calendar.model.event.EventViewer;
import seedu.address.calendar.model.util.DateUtil;

/**
 * Represents a view only month.
 */
public class ViewOnlyMonth {
    private MonthOfYear monthOfYear;
    private List<ViewOnlyDay> days;
    private Year year;

    /**
     * Creates a {@code ViewOnlyMonth} instance.
     *
     * @param monthOfYear The desired month of year that is associated with the view only month
     * @param year The desired year that is associated with the view only month
     */
    public ViewOnlyMonth(MonthOfYear monthOfYear, Year year) {
        this.monthOfYear = monthOfYear;
        this.year = year;
        days = DateUtil.getDaysOfMonth(monthOfYear, year)
                .stream()
                .map(ViewOnlyDay::fromDay)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ViewOnlyMonth(MonthOfYear monthOfYear, Year year, List<ViewOnlyDay> days) {
        this.monthOfYear = monthOfYear;
        this.year = year;
        this.days = days;
    }

    /**
     * Gets the required view only month with the specified event viewer and event query.
     *
     * @param eventViewer The specified event viewer
     * @param eventQuery The specified event query
     * @return The required view only month
     */
    public static ViewOnlyMonth getInstance(EventViewer eventViewer, EventQuery eventQuery) {
        MonthOfYear startMonth = eventQuery.getStart().getMonth();
        Year startYear = eventQuery.getStart().getYear();

        List<ViewOnlyDay> days = DateUtil.getDaysOfMonth(startMonth, startYear)
                .stream()
                .map(ViewOnlyDay::fromDay)
                .collect(Collectors.toCollection(ArrayList::new));

        Date firstDateOfMonth = DateUtil.getFirstDateInMonth(startMonth, startYear);
        Date lastDateOfMonth = DateUtil.getLastDateInMonth(startMonth, startYear);
        EventQuery monthQuery = new EventQuery(firstDateOfMonth, lastDateOfMonth);

        Stream<Event> relevantEvents = eventViewer.getEvents(monthQuery);
        relevantEvents.forEach(event -> addEventToDays(days, event, monthQuery));
        return new ViewOnlyMonth(startMonth, startYear, days);
    }

    /**
     * Adds the relevant event to the days of interest.
     *
     * @param days The days of interest
     * @param event The relevant event
     * @param eventQuery The relevant period of time
     */
    private static void addEventToDays(List<ViewOnlyDay> days, Event event, EventQuery eventQuery) {
        boolean isEventStartWithinMonth = !eventQuery.isStartsAfter(event.getStart());
        boolean isEventEndWithinMonth = !event.isEndsAfter(eventQuery.getEnd());

        int startDayOfMonth;
        int endDayOfMonth;
        EventType eventType = event.getEventType();

        if (isEventEndWithinMonth && isEventStartWithinMonth) {
            startDayOfMonth = event.getStart().getDay().getDayOfMonth();
            endDayOfMonth = event.getEnd().getDay().getDayOfMonth();
        } else if (isEventEndWithinMonth) {
            startDayOfMonth = 1; // every month starts on the first
            endDayOfMonth = event.getEnd().getDay().getDayOfMonth();
        } else if (isEventStartWithinMonth) {
            startDayOfMonth = event.getStart().getDay().getDayOfMonth();
            endDayOfMonth = days.size();
        } else {
            startDayOfMonth = 1;
            endDayOfMonth = days.size();
        }

        addEventToDays(days, startDayOfMonth, endDayOfMonth, eventType);
    }

    /**
     * Adds event to days.
     *
     * @param days The days that are of interest
     * @param startDayOfMonth The start day of the month
     * @param endDayOfMonth  The last day of the month
     * @param eventType The type of event to be added
     */
    private static void addEventToDays(List<ViewOnlyDay> days, int startDayOfMonth, int endDayOfMonth,
                                       EventType eventType) {
        IntStream.rangeClosed(startDayOfMonth, endDayOfMonth)
                .map(i -> i - 1) // to make indices zero-based
                .forEach(i -> {
                    ViewOnlyDay day = days.get(i);
                    day.addEventType(eventType);
                });
    }

    public Year getYear() {
        return year;
    }

    public MonthOfYear getMonthOfYear() {
        return monthOfYear;
    }

    public Stream<ViewOnlyDay> getDaysInMonth() {
        return days.stream();
    }

    public ViewOnlyDay getFirstDayOfMonth() {
        return days.get(0);
    }

    /**
     * Copies the specified view only month.
     *
     * @param viewOnlyMonthToCopy The specified view only month
     * @return A copy of the specified view only month
     */
    public static ViewOnlyMonth copy(ViewOnlyMonth viewOnlyMonthToCopy) {
        List<ViewOnlyDay> copiedDays = List.copyOf(viewOnlyMonthToCopy.days);
        return new ViewOnlyMonth(viewOnlyMonthToCopy.monthOfYear, viewOnlyMonthToCopy.year, copiedDays);
    }
}
