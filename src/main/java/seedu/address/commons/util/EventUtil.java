package seedu.address.commons.util;

import java.time.LocalDateTime;

import jfxtras.icalendarfx.components.VEvent;
import jfxtras.icalendarfx.properties.component.recurrence.RecurrenceRule;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.model.event.RecurrenceType;

/**
 * EventUtil contains methods for manipulation of Event and VEvents
 */
public class EventUtil {
    private static final String DAILY_RECUR_RULE_STRING = "FREQ=DAILY;INTERVAL=1";
    private static final String WEEKLY_RECUR_RULE_STRING = "FREQ=WEEKLY;INTERVAL=1";

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

        if (vEventToMap.getRecurrenceRule() == null) {
            resultEvent.setRecurrenceType(RecurrenceType.NONE);
        } else if (vEventToMap.getRecurrenceRule().toString().contains("DAILY")) {
            resultEvent.setRecurrenceType(RecurrenceType.DAILY);
        } else if (vEventToMap.getRecurrenceRule().toString().contains("WEEKLY")) {
            resultEvent.setRecurrenceType(RecurrenceType.WEEKLY);
        }

        String colorCategory = vEventToMap.getCategories().get(0).getValue().get(0);
        resultEvent.setColorCategory(colorCategory);

        return resultEvent;
    }

    /**
     * Converts a recurrenceString to a RecurrenceRule object.
     * @param recurrenceString
     * @return returns a RecurrenceRule object which is used to configure VEVents
     * @throws IllegalValueException for invalid recurrenceString.
     */
    public static RecurrenceRule stringToRecurrenceRule(String recurrenceString) throws IllegalValueException {
        recurrenceString = recurrenceString.toLowerCase();
        if (recurrenceString.equals("weekly")) {
            return RecurrenceRule.parse(recurrenceString);
        } else if (recurrenceString.equals("daily")) {
            return RecurrenceRule.parse(recurrenceString);
        } else if (recurrenceString.equals("none")) {
            return new RecurrenceRule();
        } else {
            throw new IllegalValueException("recurrence string type is not valid. value passedL " + recurrenceString);
        }
    }

    /**
     * Generates a unique identifier for VEvents using current dateTime and the following parameters
     * @param eventName name of event
     * @param startDateTime startDateTime string representation of event
     * @param endDateTime endDateTime string representation of event
     * @return a unique string identifier based on the current DateTime.
     */
    public static String generateUID(String eventName, String startDateTime, String endDateTime) {
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
}
