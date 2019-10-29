package seedu.address.model.calendar;

import seedu.address.model.member.MemberName;
import net.fortuna.ical4j.model.Calendar;

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
}