package seedu.address.model.events;
//Date have problem.
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

//TODO: Stub models for now
/**
 * Represents a Date time in the schedule.
 * Guarantees: immutable; is valid as declared in {@link #isValidTiming(String)}
 */
public class Timing {
    private static SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
    private static int PERIOD = 30;

    public static final String MESSAGE_CONSTRAINTS =
        "The event start datetime must be before the end datetime.";
    private final Date startTiming;
    private final Date endTiming;

    /**
     * Constructs a {@code Timing}.
     *
     * @param startTiming A valid dateTime describing the start of event.
     * @param endTiming   A valid dateTime describing the end of event.
     */
//    public Timing(Date startTiming, Date endTiming) {
//        requireAllNonNull(startTiming, endTiming);
////        checkArgument(isValidTiming(startTiming, endTiming), MESSAGE_CONSTRAINTS);
//
//        this.startTiming = startTiming;
//        this.endTiming = endTiming;
//    }

    /**
     * Constructs a {@code Timing}.
     *
     * @param stringTiming A valid dateTime describing the start of event.
     */
    public Timing(String stringTiming) {
        requireAllNonNull(stringTiming);
        checkArgument(isValidTiming(stringTiming), MESSAGE_CONSTRAINTS);
//        this.startTiming = formatter.parse(stringTiming);
//        this.endTiming = addMinutesToDate(PERIOD, startTiming);
    }

    /**
     * Returns true if the start dateTime is before the end dateTime.
     */

    public static boolean isValidTiming(String testStart) {
        try{

            startTiming = formatter.parse(testStart);
            endTiming = addMinutesToDate(PERIOD, startTiming);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static Date getStartingDate(String stingTime) throws ParseException {
        return formatter.parse(stingTime);
    }


    public Date getStartTiming() {
        return startTiming;
    }

    public Date getEndTiming() {
        return endTiming;
    }

    private static Date addMinutesToDate(int minutes, Date beforeTime){
        final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs

        long curTimeInMs = beforeTime.getTime();
        Date afterAddingMins = new Date(curTimeInMs + (minutes * ONE_MINUTE_IN_MILLIS));
        return afterAddingMins;
    }

    @Override
    public String toString() {
        return "SOME Date"; //TODO:
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
        return otherTiming.getStartTiming().equals(getStartTiming())
            && otherTiming.getEndTiming().equals(getEndTiming());
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTiming, endTiming);
    }
}

