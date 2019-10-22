package seedu.address.logic.processor;

import seedu.address.model.Model;
import seedu.address.model.distinctDate.DistinctDate;
import seedu.address.model.event.Event;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DistinctDatesProcessor {

    public static List<DistinctDate> generateDistinctDateList(Model model) {
        List<DistinctDate> distinctDateList = new ArrayList<>();
        List<LocalDate> dates = generateDateList(model);
        for (int i = 0; i < dates.size(); i ++) {
            LocalDate currentDate = dates.get(i);
            List<Event> events = generateListOfEventForDate(currentDate, model);
            DistinctDate date = new DistinctDate(currentDate, events);
            distinctDateList.add(date);
        }
        return distinctDateList;
    }

    public static List<Event> generateListOfEventForDate(LocalDate date, Model model) {
        List<Event> eventsOnSpecificDate = new ArrayList<>();
        List<Event> events = model.getEventBook().getEventList();
        for(int i = 0; i < events.size(); i++) {
            Event currentEvent = events.get(i);
            if (currentEvent.getListOfEventDates().contains(date)) {
                eventsOnSpecificDate.add(currentEvent);
            }
        }
        return eventsOnSpecificDate;
    }

    public static List<LocalDate> generateDateList(Model model) {
        Set<LocalDate> datesSet = new HashSet<>();
        List<Event> events = model.getEventBook().getEventList();
        for(int i = 0; i < events.size(); i++) {
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
