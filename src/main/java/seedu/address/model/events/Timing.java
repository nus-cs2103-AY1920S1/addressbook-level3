package seedu.address.model.events;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Date;
import java.util.Objects;

//TODO: Stub models for now
/**
 * Represents a Date time in the schedule.
 * Guarantees: immutable; is valid as declared in {@link #isValidTiming(Date, Date)}
 */
public class Timing {

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
    public Timing(Date startTiming, Date endTiming) {
        requireAllNonNull(startTiming, endTiming);
        checkArgument(isValidTiming(startTiming, endTiming), MESSAGE_CONSTRAINTS);

        this.startTiming = startTiming;
        this.endTiming = endTiming;
    }

    /**
     * Returns true if the start dateTime is before the end dateTime.
     */
    public static boolean isValidTiming(Date testStart, Date testEnd) {
        return testStart.before(testEnd);
    }

    public Date getStartTiming() {
        return startTiming;
    }

    public Date getEndTiming() {
        return endTiming;
    }

    @Override
    public String toString() {
        return "SOME DATETIME"; //TODO:
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
