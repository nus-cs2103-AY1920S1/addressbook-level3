package seedu.address.calendar.model.date;

import seedu.address.calendar.model.event.EventType;

/**
 * Represents a view only day.
 */
public class ViewOnlyDay {
    private DayOfWeek dayOfWeek;
    private int dayOfMonth;
    private boolean hasCommitment = false;
    private boolean hasHoliday = false;
    private boolean hasSchoolBreak = false;
    private boolean hasTrip = false;

    private ViewOnlyDay(DayOfWeek dayOfWeek, int dayOfMonth) {
        this.dayOfWeek = dayOfWeek;
        this.dayOfMonth = dayOfMonth;
    }

    public static ViewOnlyDay fromDay(Day day) {
        return new ViewOnlyDay(day.getDayOfWeek(), day.getDayOfMonth());
    }

    /**
     * Adds the specified event type to {@code this}.
     *
     * @param eventType The event type to be added
     */
    public void addEventType(EventType eventType) {
        switch (eventType) {
        case COMMITMENT:
            hasCommitment = true;
            break;
        case HOLIDAY:
            hasHoliday = true;
            break;
        case SCHOOL_BREAK:
            hasSchoolBreak = true;
            break;
        case TRIP:
            hasTrip = true;
            break;
        }
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    /**
     * Returns day of week as a meaningful numerical value that is zero-based.
     *
     * @return day of week as a meaningful numerical value that is zero-based
     */
    public int getDayOfWeekZeroIndex() {
        return dayOfWeek.getNumericalVal();
    }

    public boolean hasCommitment() {
        return hasCommitment;
    }

    public boolean hasHoliday() {
        return hasHoliday;
    }

    public boolean hasSchoolBreak() {
        return hasSchoolBreak;
    }

    public boolean hasTrip() {
        return hasTrip;
    }
}
