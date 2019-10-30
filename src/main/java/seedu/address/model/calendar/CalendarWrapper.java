package seedu.address.model.calendar;

import java.time.Duration;
import java.util.Iterator;

import seedu.address.model.member.MemberName;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Period;
import net.fortuna.ical4j.model.PeriodList;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.*;

import org.apache.commons.lang3.time.DateUtils;

public class CalendarWrapper {
    private final MemberName memberName;
    private final Calendar calendar;

    public CalendarWrapper (MemberName memberName, Calendar calendar) {
        this.memberName = memberName;
        this.calendar = calendar;
    }

    public MemberName getMemberName() {
        return memberName;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public boolean isSameCalendar(CalendarWrapper otherCalendar) {
        if (otherCalendar == this) {
            return true;
        }

        return otherCalendar != null
                && otherCalendar.getMemberName().equals(getMemberName())
                && otherCalendar.getCalendar().equals(getCalendar());
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
}
