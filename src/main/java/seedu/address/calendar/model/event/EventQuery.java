package seedu.address.calendar.model.event;

import seedu.address.calendar.model.date.Date;
import seedu.address.calendar.model.util.Interval;

public class EventQuery extends Interval<Date, EventQuery> {
    protected Date startDate;
    protected Date endDate; // todo: update

    public EventQuery(Date startDate, Date endDate) {
        super(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    boolean isSameInterval(EventQuery otherEventQuery) {
        Date otherStartDate = otherEventQuery.startDate;
        Date otherEndDate = otherEventQuery.endDate;
        return startDate.equals(otherStartDate) && endDate.equals(otherEndDate);
    }

    public static boolean isValidEventTime(Date startDate, Date endDate) {
        return startDate.compareTo(endDate) <= 0;
    }
    /*
    public boolean isEndsAfter(Date date) {
        // todo: change to endDate
        return endDate.compareTo(date) > 0;
    }

    public boolean isStartsAfter(Date date) {
        return startDate.compareTo(date) > 0;
    }

    public boolean contains(Date date) {
        boolean isStartBeforeOrAt = startDate.compareTo(date) <= 0;
        boolean isEndsAfterOrAt = endDate.compareTo(date) >= 0;
        return isStartBeforeOrAt && isEndsAfterOrAt;
    }

    public Date getStart() {
        return startDate;
    }

    public Date getEnd() {
        return endDate;
    }

    public int compareTo(Interval<Date, EventQuery> other) {
        Date otherStartDate = other.getStart();
        int startCompare = startDate.compareTo(otherStartDate);
        if (startCompare != 0) {
            return startCompare;
        }

        Date otherEndDate = other.getEnd();
        int endCompare = endDate.compareTo(otherEndDate);
        return endCompare;
    }*/
    @Override
    public String toString() {
        boolean isOneDayLong = startDate.equals(endDate);
        if (isOneDayLong) {
            return String.format("On %s", startDate);
        }
        return String.format("From %s to %s", startDate, endDate);
    }
}
