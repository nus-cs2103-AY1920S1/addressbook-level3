package seedu.address.commons.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

import org.apache.commons.math3.util.Pair;

import jfxtras.icalendarfx.components.VEvent;
import jfxtras.icalendarfx.properties.component.recurrence.RecurrenceRule;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventScheduleViewMode;
import seedu.address.model.event.RecurrenceType;

/**
 * EventUtil contains methods for manipulation of Event and VEvents
 */
public class EventUtil {
    public static final String DAILY_RECUR_RULE_STRING = "FREQ=DAILY;INTERVAL=1";
    public static final String WEEKLY_RECUR_RULE_STRING = "FREQ=WEEKLY;INTERVAL=1";
    public static final String NONE_RECUR_RULE_STRING = "FREQ=YEARLY;COUNT=1";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd[ HH:mm:ss]")
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .toFormatter();

    public static final String BAD_DATE_FORMAT = "Invalid DateTime Format. "
            + "Please follow the format: yyyy-MM-ddTHH:mm, "
            + "e.g. 28 October 2019, 2PM should be input as 2019-10-28T14:00";
    public static final String INVALID_RECURRENCE_TYPE = "Invalid Recurrence Type";

    /**
     * Maps a event to VEvent
     */
    public static VEvent eventToVEventMapper(Event eventToMap) {
        VEvent resultVEvent = new VEvent();
        resultVEvent.setDateTimeStart(eventToMap.getStartDateTime());
        resultVEvent.setDateTimeEnd(eventToMap.getEndDateTime());
        resultVEvent.setUniqueIdentifier(eventToMap.getUniqueIdentifier());
        resultVEvent.setSummary(eventToMap.getEventName());

        if (eventToMap.getRecurrenceType() == RecurrenceType.DAILY) {
            resultVEvent.setRecurrenceRule(DAILY_RECUR_RULE_STRING);
        } else if (eventToMap.getRecurrenceType() == RecurrenceType.WEEKLY) {
            resultVEvent.setRecurrenceRule(WEEKLY_RECUR_RULE_STRING);
        } else if (eventToMap.getRecurrenceType() == RecurrenceType.NONE) {
            resultVEvent.setRecurrenceRule(NONE_RECUR_RULE_STRING);
        }

        resultVEvent.withCategories(eventToMap.getColorCategory());

        return resultVEvent;
    }

    /**
     * Maps a vEvent to event
     */
    public static Event vEventToEventMapper(VEvent vEventToMap) {
        Event resultEvent = new Event();
        LocalDateTime startDateTime = LocalDateTime.parse(vEventToMap.getDateTimeStart().getValue().toString());
        LocalDateTime endDateTime = LocalDateTime.parse(vEventToMap.getDateTimeEnd().getValue().toString());
        String uniqueIdentifier = vEventToMap.getUniqueIdentifier().getValue();
        String eventName = vEventToMap.getSummary().getValue();
        resultEvent.setStartDateTime(startDateTime);
        resultEvent.setEndDateTime(endDateTime);
        resultEvent.setUniqueIdentifier(uniqueIdentifier);
        resultEvent.setEventName(eventName);

        RecurrenceRule currentRule = vEventToMap.getRecurrenceRule();
        if (currentRule.toString().contains("DAILY")) {
            resultEvent.setRecurrenceType(RecurrenceType.DAILY);
        } else if (currentRule.toString().contains("WEEKLY")) {
            resultEvent.setRecurrenceType(RecurrenceType.WEEKLY);
        } else {
            resultEvent.setRecurrenceType(RecurrenceType.NONE);
        }

        String colorCategory = vEventToMap.getCategories().get(0).getValue().get(0);
        resultEvent.setColorCategory(colorCategory);

        return resultEvent;
    }

    /**
     * Generates a unique identifier for VEvents using current dateTime and the following parameters
     * @param eventName name of event
     * @param startDateTime startDateTime string representation of event
     * @param endDateTime endDateTime string representation of event
     * @return a unique string identifier based on the current DateTime.
     */
    public static String generateUniqueIdentifier(String eventName, String startDateTime, String endDateTime) {
        StringBuilder sb = new StringBuilder();
        sb.append(LocalDateTime.now().toString());
        sb.append("-");
        sb.append(eventName);
        sb.append("-");
        sb.append(startDateTime);
        sb.append("-");
        sb.append(endDateTime);
        sb.append(".njoyAssistant");
        return sb.toString();
    }

    /**
     * Custom method to convert a date to localDateTime object with default time values
     * @param date date to be converted in the form of yyyy-mm-dd
     */
    public static LocalDateTime dateToLocalDateTimeFormatter(String date) {
        return LocalDateTime.parse(date, DATE_TIME_FORMATTER);
    }

    /**
     * Convert viewMode to its Enum Type: EventScheduleViewMode
     * @param viewMode String representation of the viewMode desired
     * @return corresponding EventScheduleViewMode enumeration
     * @throws IllegalValueException if viewMode is not equilavent to any of the viewModes
     */
    public static EventScheduleViewMode stringToEventScheduleViewMode(String viewMode) throws IllegalValueException {
        if (viewMode.equalsIgnoreCase(EventScheduleViewMode.WEEKLY.name())) {
            return EventScheduleViewMode.WEEKLY;
        } else if (viewMode.equalsIgnoreCase(EventScheduleViewMode.DAILY.name())) {
            return EventScheduleViewMode.DAILY;
        } else {
            throw new IllegalValueException("view mode string type is not valid. value passed: " + viewMode);
        }
    }

    /**
     * Validates that startDateTime provided is earlier than endDateTime provided
     * @param startDateTime LocalDateTime representation of the startDateTime
     * @param endDateTime LocalDateTime representation of the endDateTime
     * @return true if startDateTime is earlier than endDateTime
     */
    public static boolean validateStartEndDateTime(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return startDateTime.compareTo(endDateTime) < 0;
    }

    /**
     * Formats a vEvent to be printed
     * @param vEvent to convert into string format
     * @return a string representation of the vEvent
     */
    public static String vEventToString(VEvent vEvent) {
        return String.format("event name: %s || start datetime: %s || end datetime: %s\n",
                vEvent.getSummary().getValue(),
                vEvent.getDateTimeStart().getValue().toString(),
                vEvent.getDateTimeEnd().getValue().toString());
    }

    /**
     * Compares between 2 vEvents to see whether they are the same. Attributes used to determine this include
     * event name, start and end date time
     * @param vEvent1 first event to be compared
     * @param vEvent2 second event to be compared
     * @return true if both vEvents are the same
     */
    public static boolean isSameVEvent(VEvent vEvent1, VEvent vEvent2) {
        return vEvent1.getSummary().equals(vEvent2.getSummary())
                && vEvent1.getDateTimeStart().equals(vEvent2.getDateTimeStart())
                && vEvent1.getDateTimeEnd().equals(vEvent2.getDateTimeEnd());
    }

    /**
     * Formats a Index, VEvent pair to a presentable form
     *
     * @param indexVEventPair the index, vEvent pair that is to be shown to the user
     */
    public static String formatIndexVEventPair(Pair<Index, VEvent> indexVEventPair) {
        Index vEventIndex = indexVEventPair.getKey();
        VEvent vEvent = indexVEventPair.getValue();
        return String.format("Index: %d || event name: %s || start datetime: %s || end datetime: %s\n",
                vEventIndex.getOneBased(), vEvent.getSummary().getValue(),
                vEvent.getDateTimeStart().getValue().toString(),
                vEvent.getDateTimeEnd().getValue().toString());
    }
}
