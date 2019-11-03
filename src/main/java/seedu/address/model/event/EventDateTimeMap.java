package seedu.address.model.event;

import static seedu.address.logic.parser.ParserUtil.parseDate;
import static seedu.address.logic.parser.ParserUtil.parseTimePeriod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a HashMap of the EventDate to EventDayTime.
 * Used to represent the time period that the event is hosted for throughout a single day.
 */
public class EventDateTimeMap {
    public static final String MESSAGE_CONSTRAINTS =
            "EventDateTimeMap is of the format DDMMYYYY:HHMM-HHMM, delimited by commas";
    private final Map<EventDate, EventDayTime> dateTimeMap;


    public EventDateTimeMap() {
        this.dateTimeMap = new HashMap<>();
    }

    public EventDateTimeMap(Map<EventDate, EventDayTime> map) {
        dateTimeMap = map;
    }

    public EventDateTimeMap(String stringMap) throws IllegalArgumentException {
        this();
        if (stringMap.isEmpty()) {
            return;
        }

        try {
            String[] eachDateTime = stringMap.split(",");
            for (String dateTime : eachDateTime) {
                String[] dateTimeSplit = dateTime.split(":"); //[0] is date, [1] is time-period
                this.mapDateTime(parseDate(dateTimeSplit[0]), parseTimePeriod(dateTimeSplit[1]));
            }
        } catch (ArrayIndexOutOfBoundsException | ParseException e) {
            throw new IllegalArgumentException(EventDateTimeMap.MESSAGE_CONSTRAINTS);
        }

        return;
    }

    /**
     * Returns true if the given string represents a valid DateTime Mapping.
     *
     * @param test
     * @return
     */
    public static boolean isValidEventDateTimeMap(String test) {
        if (test.isEmpty()) {
            return true;
        }

        try {
            return ParserUtil.parseEventDateTimeMap(test) instanceof EventDateTimeMap;
        } catch (ParseException e) {
            return false;
        }
    }

    public Map<EventDate, EventDayTime> getDateTimeMap() {
        return dateTimeMap;
    }

    /**
     * Returns a List of EventDates that are currently mapped
     */
    public List<EventDate> getDateMappedList() {
        List<EventDate> uniqueEventDates = new ArrayList<>(dateTimeMap.keySet());
        Collections.sort(uniqueEventDates);
        return uniqueEventDates;
    }

    /**
     * Add/Update the Key-Value pair of an EventDate to a EventDayTime
     *
     * @param date    EventDate object representing a Date of an Event
     * @param dayTime EventDayTime object representing the time period in a day that the event is hosted
     */
    public void mapDateTime(EventDate date, EventDayTime dayTime) {
        dateTimeMap.put(date, dayTime);
    }


    public void clearMapping() {
        dateTimeMap.clear();
    }

    public boolean containsDateKey(EventDate date) {
        return dateTimeMap.containsKey(date);
    }

    /**
     * Gives the total number of hours (double value).
     *
     * @return
     */
    public double totalHours() {
        double totalMinutes = 0;
        for (EventDate date : dateTimeMap.keySet()) {
            totalMinutes += dateTimeMap.get(date).numMinutes();
        }

        return totalMinutes / 60.0;
    }

    /**
     * Deletes a Date from the Date-Time Mapping.
     *
     * @param date EventDate Object
     */
    public void deleteDateKey(EventDate date) {
        dateTimeMap.remove(date);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventDateTimeMap // instanceof handles nulls
                && dateTimeMap.equals(((EventDateTimeMap) other).getDateTimeMap())); // state check
    }

    @Override
    public int hashCode() {
        return dateTimeMap.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (EventDate keyEventDate : dateTimeMap.keySet()) {
            if (sb.length() > 0) {
                sb.append(","); //delimiter
            }
            sb.append(keyEventDate.toString()); //Event Date
            sb.append(":");
            sb.append(dateTimeMap.get(keyEventDate).toString());
        }
        return sb.toString();
    }

    /**
     * A similar variation of toString() for GUI display.
     */
    public String toStringWithNewLine() {
        StringBuilder sb = new StringBuilder();
        for (EventDate keyEventDate : dateTimeMap.keySet()) {

            sb.append(keyEventDate.toString()); //Event Date
            sb.append(" : ");
            sb.append(dateTimeMap.get(keyEventDate).toString() + "\n");
        }
        return sb.toString().trim();
    }
}
