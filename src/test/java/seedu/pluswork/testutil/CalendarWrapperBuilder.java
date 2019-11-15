package seedu.pluswork.testutil;

import static java.util.Objects.requireNonNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.validate.ValidationException;
import seedu.pluswork.model.calendar.CalendarWrapper;
import seedu.pluswork.model.member.MemberName;
import seedu.pluswork.model.util.SampleCalendarDataUtil;

/**
 * A utility class to help with building {@code Task} objects.
 */
public class CalendarWrapperBuilder {

    public static final Calendar DEFAULT_CALENDAR;
    public static final String DEFAULT_CALENDAR_STORAGE;
    public static final MemberName DEFAULT_MEMBERNAME = new MemberName("Johnny");

    static {
        String calendarString = SampleCalendarDataUtil.SAMPLE_CALENDAR_ABHINAV;
        DEFAULT_CALENDAR_STORAGE = calendarString;
        Calendar tmpCalendar = null;
        try {
            InputStream inputStream = new ByteArrayInputStream(calendarString.getBytes());
            CalendarBuilder builder = new CalendarBuilder();
            Calendar calendar = builder.build(inputStream);
            calendar.validate();
            tmpCalendar = calendar;
        } catch (IOException | ParserException | ValidationException e) {
            e.printStackTrace();
        }
        DEFAULT_CALENDAR = tmpCalendar;
    }

    private Calendar calendar;
    private String calendarString;
    private MemberName memberName;

    public CalendarWrapperBuilder() {
        calendar = DEFAULT_CALENDAR;
        calendarString = DEFAULT_CALENDAR_STORAGE;
        memberName = DEFAULT_MEMBERNAME;
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public CalendarWrapperBuilder(CalendarWrapper calendarToCopy) {
        requireNonNull(calendarToCopy);
        calendar = calendarToCopy.getCalendar();
        calendarString = calendarToCopy.getCalendarStorageFormat();
        memberName = calendarToCopy.getMemberName();
    }

    /**
     * Sets the {@code Name} of the {@code Task} that we are building.
     */
    public CalendarWrapperBuilder withCalendar(Calendar calendar) {
        this.calendar = calendar;
        return this;
    }

    /**
     * Sets the {@code TaskStatus} of the {@code Task} that we are building.
     */
    public CalendarWrapperBuilder withCalendarString(String calendarString) {
        this.calendarString = calendarString;
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Task} that we are building.
     */
    public CalendarWrapperBuilder withMemberName(MemberName memberName) {
        this.memberName = memberName;
        return this;
    }


    public CalendarWrapper build() {
        return new CalendarWrapper(memberName, calendar, calendarString);
    }

}
