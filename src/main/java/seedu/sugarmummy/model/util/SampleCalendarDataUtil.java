package seedu.sugarmummy.model.util;

import java.time.LocalDateTime;

import seedu.sugarmummy.model.calendar.Calendar;
import seedu.sugarmummy.model.calendar.CalendarEntry;
import seedu.sugarmummy.model.calendar.Description;
import seedu.sugarmummy.model.calendar.Event;
import seedu.sugarmummy.model.calendar.Reminder;
import seedu.sugarmummy.model.calendar.Repetition;
import seedu.sugarmummy.model.time.DateTime;

/**
 * Contains utility methods for populating SugarMummy with sample data.
 */
public class SampleCalendarDataUtil {

    public static CalendarEntry[] getSampleCalendarEntry() {
        Event appointmentEvent = new Event(new Description("Appointment"),
                new DateTime(LocalDateTime.of(2019, 11, 30, 14, 0)));
        appointmentEvent.setEndingDateTime(new DateTime(LocalDateTime.of(2019, 11, 30, 15, 40)));

        Event dinnerEvent = new Event(new Description("Dinner"), new DateTime(LocalDateTime.of(2020, 1, 1, 18, 0)));

        return new CalendarEntry[]{
            new Reminder(new Description("Insulin injection"), new DateTime(LocalDateTime.of(2020, 2, 1, 11, 30)),
                    Repetition.Daily),
            new Reminder(new Description("Medicine"), new DateTime(LocalDateTime.of(2020, 1, 2, 19, 10)),
                    Repetition.Daily),
            new Reminder(new Description("Buy medicine"), new DateTime(LocalDateTime.of(2019, 12, 2, 15, 0)),
                    Repetition.Once),
            appointmentEvent,
            dinnerEvent
        };
    }

    public static Calendar getSampleCalendar() {
        Calendar sampleCalendar = new Calendar();
        for (CalendarEntry calendarEntry : getSampleCalendarEntry()) {
            sampleCalendar.addCalendarEntry(calendarEntry);
        }
        return sampleCalendar;
    }

}
