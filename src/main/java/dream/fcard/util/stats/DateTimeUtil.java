//@@author nattanyz
package dream.fcard.util.stats;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.function.Predicate;

import dream.fcard.logic.stats.Session;
import dream.fcard.logic.stats.SessionList;
import dream.fcard.logic.storage.Schema;
import dream.fcard.util.json.exceptions.JsonWrongValueException;
import dream.fcard.util.json.jsontypes.JsonObject;
import dream.fcard.util.json.jsontypes.JsonValue;

/**
 * Utilities related to LocalDateTime and Duration objects.
 */
public class DateTimeUtil {

    /**
     * Calculate the duration between the given start and end times.
     * @param start The start time, in LocalDateTime form.
     * @param end The end time, in LocalDateTime form.
     * @return The duration between the start and end times, as a Duration object.
     */
    public static Duration calculateDuration(LocalDateTime start, LocalDateTime end) {
        return Duration.between(start, end);
    }

    /**
     * Returns the String representation of the given Duration object.
     * Format: "X hours Y minutes Z seconds"
     * @param duration The duration to be represented as a String.
     * @return The String representation of the given Duration object.
     */
    public static String getStringFromDuration(Duration duration) {
        Predicate<String> equalsZero = (s -> s.equals("0"));

        StringBuilder sb = new StringBuilder();
        String hours = Long.toString(duration.toHoursPart());
        String minutes = Long.toString(duration.toMinutesPart());
        String seconds = Long.toString(duration.toSecondsPart());

        if (!equalsZero.test(hours)) {
            sb.append(hours).append(" hours ");
        }

        if (!equalsZero.test(minutes)) {
            sb.append(minutes).append(" minutes ");
        }

        if (!equalsZero.test(seconds)) {
            sb.append(seconds).append(" seconds");
        }

        return sb.toString();
    }

    /**
     * Returns the String representation of the given LocalDateTime object.
     * Format: "M/D/Y, HH:MM AM/PM", similar to "23/8/16, 1:12 PM".
     * @param localDateTime The LocalDateTime object to be represented as a String.
     * @return The String representation of the given LocalDateTime object.
     */
    public static String getStringFromDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
        return localDateTime.format(formatter);
    }

    //@@author AHaliq
    /**
     * Returns a JsonValue of a LocalDateTime object.
     * @param localDateTime object to convert to json
     * @return              resulting JsonValue
     */
    public static JsonValue getJsonFromDateTime(LocalDateTime localDateTime) {
        JsonObject obj = new JsonObject();
        obj.put(Schema.DATE_TIME_YEAR, localDateTime.getYear());
        obj.put(Schema.DATE_TIME_MONTH, localDateTime.getMonthValue());
        obj.put(Schema.DATE_TIME_DAY, localDateTime.getDayOfMonth());
        obj.put(Schema.DATE_TIME_HOUR, localDateTime.getHour());
        obj.put(Schema.DATE_TIME_MINUTE, localDateTime.getMinute());
        obj.put(Schema.DATE_TIME_SECOND, localDateTime.getSecond());
        obj.put(Schema.DATE_TIME_NANO, localDateTime.getNano());
        return new JsonValue(obj);
    }

    /**
     * Gets the LocalDateTime value of a JsonObject.
     * @param obj The JsonObject.
     * @return The resulting LocalDateTime value.
     * @throws JsonWrongValueException
     * @throws NullPointerException
     */
    public static LocalDateTime getDateTimeFromJson(JsonObject obj) throws
            JsonWrongValueException, NullPointerException {
        return LocalDateTime.of(
                obj.get(Schema.DATE_TIME_YEAR).getInt(),
                obj.get(Schema.DATE_TIME_MONTH).getInt(),
                obj.get(Schema.DATE_TIME_DAY).getInt(),
                obj.get(Schema.DATE_TIME_HOUR).getInt(),
                obj.get(Schema.DATE_TIME_MINUTE).getInt(),
                obj.get(Schema.DATE_TIME_SECOND).getInt(),
                obj.get(Schema.DATE_TIME_NANO).getInt());
    }
    //@@author

    //@@author nattanyz

    /**
     * Computes and returns the average duration of sessions in the given SessionList.
     * @param sessionList The given SessionList.
     * @return The average duration of sessions in the given SessionList.
     */
    public static Duration getAverageDuration(SessionList sessionList) {
        Duration totalDuration = Duration.ZERO;
        ArrayList<Session> sessionsArrayList = sessionList.getSessionArrayList();
        for (Session session : sessionsArrayList) {
            totalDuration = totalDuration.plus(session.getDuration());
        }

        int numberOfSessions = sessionList.getNumberOfSessions();

        Duration averageDuration = totalDuration.dividedBy(numberOfSessions);
        return averageDuration;
    }

    /**
     * Gets the cut off date for one week before the given date, beginning at midnight.
     * @param from The given date to use in the calculation.
     * @return A LocalDateTime object representing a date one week before the given date.
     */
    public static LocalDateTime getLastWeekCutoffDate(LocalDateTime from) {
        LocalDateTime lastWeek = from.minusWeeks(1).truncatedTo(DAYS);
        return lastWeek;
    }
}
