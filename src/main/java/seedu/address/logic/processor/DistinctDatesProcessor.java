package seedu.address.logic.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.Model;
import seedu.address.model.distinctdate.DistinctDate;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;

/**
 * Contains utility methods used for processing DistinctDates.
 */
public class DistinctDatesProcessor {

    /**
     * Generates a list of DistinctDate objects based on the input eventlist from model.
     *
     * @param model to retrieve event objects from the eventlist for processing
     * @return a list of DistinctDate objects
     */
    public static List<DistinctDate> generateDistinctDateList(Model model) {
        List<DistinctDate> distinctDateList = new ArrayList<>();
        List<EventDate> dates = generateDateList(model);

        for (int i = 0; i < dates.size(); i++) {
            EventDate currentDate = dates.get(i);
            List<Event> events = generateListOfEventForDate(currentDate, model);
            DistinctDate date = new DistinctDate(currentDate, events);
            distinctDateList.add(date);
        }

        return distinctDateList;
    }

    /**
     * Generates a list of Event objects that falls on a given EventDate object.
     *
     * @param date  a EventDate object which have been identified to be Distinct.
     * @param model to retrieve event objects from the eventlist for processing
     * @return a list of Event objects that contains the specific EventDate
     */
    public static List<Event> generateListOfEventForDate(EventDate date, Model model) {
        List<Event> eventsOnSpecificDate = new ArrayList<>();
        List<Event> events = model.getEventBook().getEventList();
        for (int i = 0; i < events.size(); i++) {
            Event currentEvent = events.get(i);
            if (currentEvent.getListOfEventDates().contains(date)) {
                eventsOnSpecificDate.add(currentEvent);
            }
        }
        return eventsOnSpecificDate;
    }

    /**
     * Generates a unique, sorted list of EventDates based on the eventlist in model.
     *
     * @param model uses model to retrieve eventlist
     * @return a list of distinct dates in EventDate format
     */
    public static List<EventDate> generateDateList(Model model) {
        Set<EventDate> datesSet = new HashSet<>();
        List<Event> events = model.getEventBook().getEventList();

        for (int i = 0; i < events.size(); i++) {
            Event currentEvent = events.get(i);
            datesSet.addAll(currentEvent.getListOfEventDates());
        }

        List<EventDate> datesList = new ArrayList<>(datesSet);
        Collections.sort(datesList);
        return datesList;
    }
}
