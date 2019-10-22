package seedu.address.logic.processor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.Model;
import seedu.address.model.distinctdate.DistinctDate;
import seedu.address.model.event.Event;

/**
 * Contains utility methods used for processing DistinctDates.
 */
public class DistinctDatesProcessor {

    /**
     * Generates a list of DistinctDate objects based on the input eventlist from model.
     * @param model to retrieve event objects from the eventlist for processing
     * @return a list of DistinctDate objects
     */
    public static List<DistinctDate> generateDistinctDateList(Model model) {
        List<DistinctDate> distinctDateList = new ArrayList<>();
        List<LocalDate> dates = generateDateList(model);
        for (int i = 0; i < dates.size(); i++) {
            LocalDate currentDate = dates.get(i);
            List<Event> events = generateListOfEventForDate(currentDate, model);
            DistinctDate date = new DistinctDate(currentDate, events);
            distinctDateList.add(date);
        }
        return distinctDateList;
    }

    /**
     * Generates a list of Event objects based on the a LocalDate object from an Event.
     * @param date a LocalDate object which have been identified to be Distinct.
     * @param model to retrieve event objects from the eventlist for processing
     * @return a list of Event objects that contains the specific LocalDate
     */
    public static List<Event> generateListOfEventForDate(LocalDate date, Model model) {
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
     * Generates a list of distinct Date based on the eventlist in model.
     * @param model uses model to retrieve eventlist
     * @return a list of distinct dates in LocalDate format
     */
    public static List<LocalDate> generateDateList(Model model) {
        Set<LocalDate> datesSet = new HashSet<>();
        List<Event> events = model.getEventBook().getEventList();
        for (int i = 0; i < events.size(); i++) {
            Event currentEvent = events.get(i);
            datesSet.addAll(currentEvent.getListOfEventDates());
        }
        List<LocalDate> datesList = new ArrayList<>();
        for (LocalDate date : datesSet) {
            datesList.add(date);
        }
        return datesList;
    }
}
