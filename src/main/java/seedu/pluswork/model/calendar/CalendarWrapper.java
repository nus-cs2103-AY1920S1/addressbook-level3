package seedu.pluswork.model.calendar;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Period;
import net.fortuna.ical4j.model.PeriodList;
import net.fortuna.ical4j.model.component.VEvent;
import seedu.pluswork.model.member.MemberName;

public class CalendarWrapper {
    private final MemberName memberName;
    private final Calendar calendar;
    private final String calendarStorageFormat;

    public CalendarWrapper(MemberName memberName, Calendar calendar, String calendarStorageFormat) {
        this.memberName = memberName;
        this.calendar = calendar;
        this.calendarStorageFormat = calendarStorageFormat;
    }

    public MemberName getMemberName() {
        return memberName;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public String getCalendarStorageFormat() {
        return calendarStorageFormat;
    }

    public boolean isSameCalendar(CalendarWrapper otherCalendar) {
        if (otherCalendar == this) {
            return true;
        }

        return otherCalendar != null
                && otherCalendar.getMemberName().equals(getMemberName())
                && otherCalendar.getCalendarStorageFormat().equals(getCalendarStorageFormat())
                && otherCalendar.getCalendar().equals(getCalendar());
    }

    public boolean hasSameMemberName(CalendarWrapper otherCalendar) {
        if (otherCalendar == this) {
            return true;
        }

        return otherCalendar != null
                && otherCalendar.getMemberName().equals(getMemberName());
    }

    public boolean hasMemberName(MemberName otherCalendar) {
        if (otherCalendar == getMemberName()) {
            return true;
        }

        return otherCalendar != null
                && otherCalendar.equals(getMemberName());
    }

    public PeriodList getEventsDuringPeriod(Period searchPeriod) {
        PeriodList mainPeriodList = new PeriodList();
        for (Object o : calendar.getComponents("VEVENT")) {
            VEvent event = (VEvent) o;
            mainPeriodList = mainPeriodList.add(event.calculateRecurrenceSet(searchPeriod));
        }
        return mainPeriodList;
    }

    public PeriodList getAvailabilityDuringPeriod(Period searchPeriod) {
        PeriodList searchPeriodList = new PeriodList();
        searchPeriodList.add(searchPeriod);
        PeriodList busyTimePeriodList = getEventsDuringPeriod(searchPeriod);
        PeriodList freeTimePeriodList = searchPeriodList.subtract(busyTimePeriodList);
        return freeTimePeriodList.normalise();
    }

    @Override
    public String toString() {
        String format = "%1$s" + "'s Calendar";
        return String.format(format, memberName.fullName);
    }
}
