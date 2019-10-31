package seedu.address.commons.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;

import jfxtras.icalendarfx.components.VEvent;
import jfxtras.icalendarfx.properties.component.descriptive.Categories;
import jfxtras.icalendarfx.properties.component.recurrence.RecurrenceRule;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventScheduleViewMode;
import seedu.address.model.event.RecurrenceType;

/**
 * EventUtil contains methods for manipulation of Event and VEvents
 */
public class EventUtil {
    private static final String DAILY_RECUR_RULE_STRING = "FREQ=DAILY;INTERVAL=1";
    private static final String WEEKLY_RECUR_RULE_STRING = "FREQ=WEEKLY;INTERVAL=1";
    private static final DateTimeFormatter dateOnlyFormatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd[ HH:mm:ss]")
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .toFormatter();

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
        if (recurrenceString.equalsIgnoreCase("weekly")) {
            return RecurrenceRule.parse(recurrenceString);
        } else if (recurrenceString.equalsIgnoreCase("daily")) {
            return RecurrenceRule.parse(recurrenceString);
        } else if (recurrenceString.equalsIgnoreCase("none")) {
            return new RecurrenceRule();
        } else {
            throw new IllegalValueException("recurrence string type is not valid. value passed: " + recurrenceString);
        }
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
     * Converts a string number to format that ICalendarAgenda accepts.
     * @param number String representation of number
     * @return String representation of colorNumber as required by ICalendarAgenda
     */
    public static String convertNumberToColorNumber(String number) throws NumberFormatException{
        return "group" + (Integer.parseInt(number) < 10 ? "0" : "") + number;
    }

    /**
     * Custom method to convert a date to localDateTime object with default time values
     * @param date date to be converted in the form of yyyy-mm-dd
     */
    public static LocalDateTime dateToLocalDateTimeFormatter(String date) {
        return LocalDateTime.parse(date, dateOnlyFormatter);
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
     * Validates if a color number string is valid
     * @param colorNumberString numberString to be checked
     * @return true if colorNumberString is valid
     * @throws NumberFormatException when colorNumberString cannot be cast to Integer,
     * representing invalid string format
     */
    public static boolean validateColorNumberString(String colorNumberString) throws NumberFormatException {
        //validate number is in range
        Integer colorNumberInteger = Integer.parseInt(colorNumberString);
        boolean result = numberInRange(colorNumberInteger, 0, 23);
        return result;
    }

    /**
     * Method to check if an Integer is in range specified. Includes start and end range as valid values
     * @param number Integer to be checked
     * @param startRange smallest number allowed
     * @param endRange largest number allowed
     * @return true if number is within specified range
     */
    private static boolean numberInRange(Integer number, Integer startRange, Integer endRange) {
        if (number > endRange || number < startRange) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Validates if recurTypeString is valid based on RecurrenceType enumeration
     * @param recurTypeString String to be evaluated
     * @return true if recurTypeString is valid
     */
    public static boolean validateRecurTypeString(String recurTypeString)  {
        if (recurTypeString.equalsIgnoreCase(RecurrenceType.WEEKLY.name())) {
            return true;
        } else if (recurTypeString.equalsIgnoreCase(RecurrenceType.DAILY.name())) {
            return true;
        } else if (recurTypeString.equalsIgnoreCase(RecurrenceType.NONE.name())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * validates and converts a colorNumberString into an ArrayList of Categories
     * to be set into VEvents categories attribute.s
     * @param colorNumberString input to be converted
     * @return an array list of categories.
     */
    public static ArrayList<Categories> convertNumberToColorCategoryList(String colorNumberString)
            throws IllegalValueException {
        if (!validateColorNumberString(colorNumberString)) {
            throw new IllegalValueException("invalid color string passed.");
        }
        String colorCategoryString = convertNumberToColorNumber(colorNumberString);
        Categories colorCategory = new Categories(colorCategoryString);
        ArrayList<Categories> colorCategoryList = new ArrayList<>();
        colorCategoryList.add(colorCategory);
        return colorCategoryList;
    }
}
