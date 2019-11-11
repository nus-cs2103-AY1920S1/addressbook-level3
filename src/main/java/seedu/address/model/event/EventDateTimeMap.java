/*
@@author DivineDX
 */

package seedu.address.model.event;

import static seedu.address.logic.parser.ParserUtil.parseEventDate;
import static seedu.address.logic.parser.ParserUtil.parseTimePeriod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a TreeMap of the EventDate to EventDayTime.
 * Used to represent the schedule of an Event on all valid, mapped dates.
 */
public class EventDateTimeMap {
    public static final String MESSAGE_CONSTRAINTS =
            "EventDateTimeMap is of the format DDMMYYYY:HHMM-HHMM, delimited by commas";
    private static final String DELIMITER = ",";
    private final Map<EventDate, EventDayTime> dateTimeMap;

    /**
     * Default Constructor, initializes a new TreeMap.
     */
    public EventDateTimeMap() {
        dateTimeMap = new TreeMap<>();
    }

    public EventDateTimeMap(Map<EventDate, EventDayTime> map) {
        this();
        dateTimeMap.putAll(map);
    }

    /**
     * Create a new, immutable copy of the EventDateTimeMap
     */
    public EventDateTimeMap(EventDateTimeMap other) {
        this(other.getDateTimeMap());
    }

    /**
     * Used when constructing EventDateTimeMap object from JsonStorage.
     *
     * @param stringMap Stored String in JSON.
     * @throws IllegalArgumentException Error in Parsing.
     */
    public EventDateTimeMap(String stringMap) throws IllegalArgumentException {
        this();
        if (stringMap.isEmpty()) {
            return;
        }

        try {
            String[] eachDateTime = stringMap.split(DELIMITER); //delimiter
            for (String dateTime : eachDateTime) {
                String[] dateTimeSplit = dateTime.split(":"); //[0] is date, [1] is time-period
                this.mapDateTime(parseEventDate(dateTimeSplit[0]), parseTimePeriod(dateTimeSplit[1]));
            }
        } catch (ArrayIndexOutOfBoundsException | ParseException e) {
            throw new IllegalArgumentException(EventDateTimeMap.MESSAGE_CONSTRAINTS);
        }

        return;
    }

    /**
     * When Event object is first created, auto-initialize DateTime mapping for Start&End Date.
     */
    public void initalizeDateTime(EventDate startDate, EventDate endDate) {
        EventDayTime defaultEventDayTime = EventDayTime.defaultEventDayTime();
        mapDateTime(startDate, defaultEventDayTime);
        mapDateTime(endDate, defaultEventDayTime);
    }


    /**
     * Returns true if the given string represents a valid DateTime Mapping.
     *
     * @param test String to be parsed
     * @return boolean to indicate if the parse is successful
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
     * Returns a Sorted List of EventDates that are currently mapped
     */
    public List<EventDate> getDateMappedList() {
        List<EventDate> uniqueEventDates = new ArrayList<>(dateTimeMap.keySet());
        Collections.sort(uniqueEventDates);
        return uniqueEventDates;
    }

    /**
     * Add/Update the Key-Value pair of an EventDate to a EventDayTime.
     *
     * @param date    EventDate object representing a Date of an Event
     * @param dayTime EventDayTime object representing the time period on a date in the Event's Schedule.
     */
    public void mapDateTime(EventDate date, EventDayTime dayTime) {
        dateTimeMap.put(date, dayTime);
    }

    /**
     * Clears the entire DateTime Mapping from an Event.
     */
    public void clearMapping() {
        dateTimeMap.clear();
    }

    /**
     * Boolean that checks whether an EventDate is within
     *
     * @param date A valid {@code EventDate} object
     */
    public boolean containsDateKey(EventDate date) {
        return dateTimeMap.containsKey(date);
    }

    /**
     * Calculates the Event's total number of hours based on the sum
     * of the duration of the mapped {@EventDayTime}.
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

    /**
     * Called when the Event's Start/End Date has changed. EventDateTime will flush {@code EventDate} keys
     * from the Mapping which falls out of the new range.
     * Then, the default DateTime values will be inserted for the start and end dates keys if not found
     */
    public void flushEventDates(EventDate newStartDate, EventDate newEndDate) {
        dateTimeMap.entrySet().removeIf(event -> {
            EventDate eventDate = event.getKey();
            return eventDate.isBefore(newStartDate) || eventDate.isAfter(newEndDate);
        });
        if (!dateTimeMap.containsKey(newStartDate)) {
            mapDateTime(newStartDate, EventDayTime.defaultEventDayTime());
        }
        if (!dateTimeMap.containsKey(newEndDate)) {
            mapDateTime(newEndDate, EventDayTime.defaultEventDayTime());
        }
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
                sb.append(DELIMITER);
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
