package seedu.address.calendar.model.event;

import seedu.address.calendar.model.date.Date;
import seedu.address.calendar.model.date.MonthOfYear;
import seedu.address.calendar.model.date.Year;
import seedu.address.calendar.model.util.DateUtil;
import seedu.address.calendar.model.util.Interval;

public class EventQuery extends Interval<Date, EventQuery> {
    protected Date startDate;
    protected Date endDate;

    public EventQuery(Date startDate, Date endDate) {
        super(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static boolean isValidEventTime(Date startDate, Date endDate) {
        return startDate.compareTo(endDate) <= 0;
    }

    public static EventQuery getMonthQuery(EventQuery eventQuery) {
        Date startDate = eventQuery.getStart();
        Date startOfMonth = DateUtil.getFirstDateInSameMonth(startDate);
        Date endOfMonth = DateUtil.getLastDateInSameMonth(startDate);

        return new EventQuery(startOfMonth, endOfMonth);
    }

    boolean isSameInterval(EventQuery otherEventQuery) {
        Date otherStartDate = otherEventQuery.startDate;
        Date otherEndDate = otherEventQuery.endDate;
        return startDate.equals(otherStartDate) && endDate.equals(otherEndDate);
    }

    @Override
    public String toString() {
        boolean isOneDayLong = startDate.equals(endDate);
        if (isOneDayLong) {
            return String.format("On %s", startDate);
        }
        return String.format("From %s to %s", startDate, endDate);
    }
}
