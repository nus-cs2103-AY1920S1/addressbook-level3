package seedu.address.logic.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.Model;
import seedu.address.model.distinctdate.DistinctDate;
import seedu.address.model.employee.Employee;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;

/**
 * Contains utility methods used for processing DistinctDates.
 */
public class DistinctDatesProcessor {

    /**
     * Generates all DistinctDates from the given EventList.
     */
    public static List<DistinctDate> generateAllDistinctDateList(Model model) {
        List<Event> fullEventList = model.getFullListEvents();
        return generateDistinctDateList(fullEventList);
    }

    /**
     * Generate all DistinctDates from Events that are allocated to an Employee.
     */
    public static List<DistinctDate> generateEmployeesDistinctDateList(Model model, Employee employee) {
        List<Event> eventList = model.getFullListEvents();
        return generateEmployeesDistinctDateList(eventList, employee);
    }

    /**
     * Generate all DistinctDates from Events that are allocated to an Employee. Overloaded Method.
     *
     * @param eventList Unfiltered EventList
     */
    public static List<DistinctDate> generateEmployeesDistinctDateList(List<Event> eventList, Employee employee) {
        List<Event> filteredEventList = eventList.stream()
                .filter(event -> event.employeeIsAllocated(employee))
                .collect(Collectors.toList());

        return generateDistinctDateList(filteredEventList);
    }

    /**
     * Generates a list of DistinctDate objects based on the input eventlist from model.
     *
     * @param eventList List of Events to process
     * @return a list of DistinctDate objects
     */
    public static List<DistinctDate> generateDistinctDateList(List<Event> eventList) {
        List<DistinctDate> distinctDateList = new ArrayList<>();
        List<EventDate> dates = generateDateList(eventList); //Unique and Sorted

        for (int i = 0; i < dates.size(); i++) {
            EventDate currentDate = dates.get(i);
            List<Event> events = generateListOfEventForDate(currentDate, eventList);
            DistinctDate date = new DistinctDate(currentDate, events);
            distinctDateList.add(date);
        }
        return distinctDateList;
    }

    /**
     * Generates a list of Event objects that falls on a given EventDate object.
     *
     * @param date   a EventDate object which have been identified to be Distinct.
     * @param events List of Events to filter through
     * @return a list of Event objects that contains the specific EventDate
     */
    public static List<Event> generateListOfEventForDate(EventDate date, List<Event> events) {
        return events.stream()
                .filter(event -> event.getListOfEventDates().contains(date))
                .collect(Collectors.toList());
    }

    /**
     * Generates a unique, sorted list of mapped EventDates from the given list of events
     *
     * @param eventList given List of Events
     * @return a list of distinct dates in EventDate format
     */
    public static List<EventDate> generateDateList(List<Event> eventList) {
        Set<EventDate> datesSet = new HashSet<>();

        for (int i = 0; i < eventList.size(); i++) {
            Event currentEvent = eventList.get(i);
            datesSet.addAll(currentEvent.getListOfEventDates());
        }

        List<EventDate> datesList = new ArrayList<>(datesSet);
        Collections.sort(datesList);
        return datesList;
    }
}
