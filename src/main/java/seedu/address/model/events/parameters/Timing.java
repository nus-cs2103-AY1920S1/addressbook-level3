//@@author SakuraBlossom
package seedu.address.model.events.parameters;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Objects;


/**
 * Represents a Date time in the schedule.
 * Guarantees: immutable; is valid as declared in {@link #isValidTimingFromCurrentTime(DateTime)}
 */
public class Timing implements Comparable<Timing> {

    public static final String MESSAGE_CONSTRAINTS =
            "The event start timing must be before the end timing and after current dateTime.";

    private final DateTime startTiming;
    private final DateTime endTiming;

    /**
     * Constructs a {@code Timing}.
     *
     * @param startTiming A valid dateTime describing the start of event.
     * @param endTiming   A valid dateTime describing the end of event.
     */
    public Timing(DateTime startTiming, DateTime endTiming) {
        requireAllNonNull(startTiming, endTiming);
        checkArgument(isValidTiming(startTiming, endTiming), MESSAGE_CONSTRAINTS);

        this.startTiming = startTiming;
        this.endTiming = endTiming;
    }

    /**
     * Constructs a {@code Timing}.
     *
     * @param startTiming A valid dateTime describing the start of event.
     */
    public Timing(DateTime startTiming) {
        requireAllNonNull(startTiming);
        this.startTiming = startTiming;
        this.endTiming = DateTime.plusHalfHour(startTiming);
        new Timing(this.startTiming, this.endTiming);
    }

    /**
     * Returns true if the start dateTime is before the end dateTime.
     */
    public static boolean isValidTiming(DateTime testStart, DateTime testEnd) {
        requireAllNonNull(testStart, testEnd);
        return testStart.getTime().isBefore(testEnd.getTime());
    }

    /**
     * Returns true if the start dateTime is before current time.
     */
    public static boolean isValidTimingFromCurrentTime(DateTime testStart) {
        return testStart.getTime().isAfter(LocalDateTime.now());
    }

    //timing is before endtime and after opening time
    //have the event timing, ca ack only when current time is on that day.
    /**
     * Returns true if current time and appointment's timing os on the same date and before endTiming.
     */
    public static boolean isTheSameDayToAck(Timing timing) {
        LocalDateTime openTimeOnThatDay = timing.getStartTime().getTime().withHour(0).withMinute(0);
        LocalDateTime current = LocalDateTime.now();
        return current.isAfter(openTimeOnThatDay) && current.isBefore(timing.getEndTime().getTime());
    }

    public DateTime getStartTime() {
        return startTiming;
    }

    public DateTime getEndTime() {
        return endTiming;
    }

    /**
     * Returns true if the endtime is before current time.
     */
    public Boolean hasMissedTiming() {
        return getEndTime().getTime().isBefore(LocalDateTime.now());
    }

    /**
     * Returns true if the another timing is staggering within the start or end dateTime.
     */
    public boolean conflictsWith(Timing other) {
        requireNonNull(other);
        return other != this
                && getStartTime().before(other.getEndTime())
                && other.getStartTime().before(getEndTime());
    }

    /**
     * gets another Timing object which is one day later from current one.
     *
     * @param current a given Timing object
     * @return a {@code Timing} new object which is one day later from given one.
     */
    public static Timing getOneDayLaterTiming(Timing current) {
        DateTime start = DateTime.plusOneDay(current.getStartTime());
        DateTime end = DateTime.plusOneDay(current.getEndTime());
        return new Timing(start, end);
    }

    /**
     * gets another Timing object which is one week later from current one.
     *
     * @param current a given Timing object
     * @return a {@code Timing} new object which is one month later from given one.
     */
    public static Timing getOneWeekLaterTiming(Timing current) {
        DateTime start = DateTime.plusOneWeek(current.getStartTime());
        DateTime end = DateTime.plusOneWeek(current.getEndTime());
        return new Timing(start, end);
    }

    /**
     * gets another Timing object which is one month later from current one.
     *
     * @param current a given Timing object
     * @return a {@code Timing} new object which is one month later from given one.
     */
    public static Timing getOneMonthLaterTiming(Timing current) {
        DateTime start = DateTime.plusOneMonth(current.getStartTime());
        DateTime end = DateTime.plusOneMonth(current.getEndTime());
        return new Timing(start, end);
    }

    /**
     * gets another Timing object which is one year later from current one.
     *
     * @param current a given Timing object
     * @return a {@code Timing} new object which is one year later from given one.
     */
    public static Timing getOneYearLaterTiming(Timing current) {
        DateTime start = DateTime.plusOneYear(current.getStartTime());
        DateTime end = DateTime.plusOneYear(current.getEndTime());
        return new Timing(start, end);
    }

    @Override
    public String toString() {
        return String.format("%s - %s", startTiming.toString(), endTiming.toString());
    }

    @Override
    public int compareTo(Timing t) {
        requireNonNull(t);
        int cmpStartTimingResult = t.getStartTime().compareTo(getStartTime());
        if (cmpStartTimingResult != 0) {
            return cmpStartTimingResult;
        }

        return t.getEndTime().compareTo(getEndTime());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Timing)) {
            return false;
        }

        Timing otherTiming = (Timing) other;
        return otherTiming.getStartTime().equals(getStartTime())
                && otherTiming.getEndTime().equals(getEndTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTiming, endTiming);
    }
}
