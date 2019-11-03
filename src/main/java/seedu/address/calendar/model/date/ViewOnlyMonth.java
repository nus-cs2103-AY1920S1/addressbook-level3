package seedu.address.calendar.model.date;

import seedu.address.calendar.model.event.Event;
import seedu.address.calendar.model.event.EventManager;
import seedu.address.calendar.model.event.EventQuery;
import seedu.address.calendar.model.event.EventType;
import seedu.address.calendar.model.util.DateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ViewOnlyMonth {
    private MonthOfYear monthOfYear;
    private List<ViewOnlyDay> days;
    private Year year;

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

    // todo: consider implementing an interface for eventmanager instead
    public static ViewOnlyMonth getInstance(EventManager eventManager, EventQuery eventQuery) {
        MonthOfYear startMonth = eventQuery.getStart().getMonth();
        Year startYear = eventQuery.getStart().getYear();

        List<ViewOnlyDay> days = DateUtil.getDaysOfMonth(startMonth, startYear)
                .stream()
                .map(ViewOnlyDay::fromDay)
                .collect(Collectors.toCollection(ArrayList::new));

        Date firstDateOfMonth = DateUtil.getFirstDateInMonth(startMonth, startYear);
        Date lastDateOfMonth = DateUtil.getLastDateInMonth(startMonth, startYear);
        EventQuery monthQuery = new EventQuery(firstDateOfMonth, lastDateOfMonth);

        Stream<Event> relevantEvents = eventManager.getEvents(monthQuery);
        relevantEvents.forEach(event -> addEventToDays(days, event, monthQuery));
        return new ViewOnlyMonth(startMonth, startYear, days);
    }

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

    public static ViewOnlyMonth copy(ViewOnlyMonth viewOnlyMonthToCopy) {
        List<ViewOnlyDay> copiedDays = List.copyOf(viewOnlyMonthToCopy.days);
        return new ViewOnlyMonth(viewOnlyMonthToCopy.monthOfYear, viewOnlyMonthToCopy.year, copiedDays);
    }
}
