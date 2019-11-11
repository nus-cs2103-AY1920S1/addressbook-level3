package seedu.sugarmummy.model.util;

import java.time.LocalDateTime;

import seedu.sugarmummy.model.calendar.Calendar;
import seedu.sugarmummy.model.calendar.CalendarEntry;
import seedu.sugarmummy.model.calendar.Description;
import seedu.sugarmummy.model.calendar.Event;
import seedu.sugarmummy.model.calendar.Reminder;
import seedu.sugarmummy.model.calendar.Repetition;
import seedu.sugarmummy.model.records.BloodSugarBuilder;
import seedu.sugarmummy.model.records.BmiBuilder;
import seedu.sugarmummy.model.records.Record;
import seedu.sugarmummy.model.records.UniqueRecordList;
import seedu.sugarmummy.model.time.DateTime;

/**
 * Contains utility methods for populating SugarMummy with sample data.
 */
public class SampleRecordDataUtil {

    public static Record[] getSampleRecords() {
        return new Record[]{
                new BmiBuilder().build(),
                new BmiBuilder().withDateTime("2019-02-03 09:29").withHeight("1.8").withWeight("67.0").build(),
                new BmiBuilder().withDateTime("2019-03-04 09:39").withHeight("1.8").withWeight("66.3").build(),
                new BmiBuilder().withDateTime("2019-04-05 09:49").withHeight("1.8").withWeight("65.1").build(),
                new BmiBuilder().withDateTime("2019-05-06 09:59").withHeight("1.8").withWeight("63.2").build(),
                new BmiBuilder().withDateTime("2019-06-07 07:09").withHeight("1.8").withWeight("60.1").build(),
                new BmiBuilder().withDateTime("2019-07-02 09:09").withHeight("1.8").withWeight("90.0").build(),
                new BmiBuilder().withDateTime("2019-07-13 12:19").withHeight("1.8").withWeight("85.0").build(),
                new BmiBuilder().withDateTime("2019-07-26 09:19").withHeight("1.8").withWeight("90.5").build(),
                new BmiBuilder().withDateTime("2019-11-01 06:09").withHeight("1.8").withWeight("88.2").build(),
                new BmiBuilder().withDateTime("2019-11-03 05:09").withHeight("1.8").withWeight("100.3").build(),
                new BmiBuilder().withDateTime("2019-11-10 05:09").withHeight("1.8").withWeight("65.3").build(),
                new BmiBuilder().withDateTime("2019-11-11 00:09").withHeight("1.8").withWeight("50.3").build(),
                new BloodSugarBuilder().build(),
                new BloodSugarBuilder().withDateTime("2019-01-03 09:09").withConcentration("8.6").build(),
                new BloodSugarBuilder().withDateTime("2019-01-03 09:10").withConcentration("8.7").build(),
                new BloodSugarBuilder().withDateTime("2019-01-04 09:11").withConcentration("7.4").build(),
                new BloodSugarBuilder().withDateTime("2019-01-05 09:12").withConcentration("4.9").build(),
                new BloodSugarBuilder().withDateTime("2019-01-06 09:13").withConcentration("7.4").build(),
                new BloodSugarBuilder().withDateTime("2019-01-07 09:14").withConcentration("7.0").build(),
                new BloodSugarBuilder().withDateTime("2019-01-08 09:15").withConcentration("5.0").build(),
                new BloodSugarBuilder().withDateTime("2019-01-09 09:16").withConcentration("4.2").build(),
                new BloodSugarBuilder().withDateTime("2019-01-10 09:17").withConcentration("4.5").build(),
                new BloodSugarBuilder().withDateTime("2019-02-03 09:18").withConcentration("7.0").build(),
                new BloodSugarBuilder().withDateTime("2019-02-04 09:19").withConcentration("6.2").build(),
                new BloodSugarBuilder().withDateTime("2019-03-03 09:20").withConcentration("8.9").build(),
                new BloodSugarBuilder().withDateTime("2019-03-04 09:21").withConcentration("6.8").build(),
                new BloodSugarBuilder().withDateTime("2019-04-03 09:22").withConcentration("6.9").build(),
                new BloodSugarBuilder().withDateTime("2019-04-04 09:23").withConcentration("4.0").build()
        };
    }

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

    public static UniqueRecordList getSampleRecordList() {
        UniqueRecordList sampleRl = new UniqueRecordList();
        for (Record sampleRecord : getSampleRecords()) {
            sampleRl.add(sampleRecord);
        }
        return sampleRl;
    }

    public static Calendar getSampleCalendar() {
        Calendar sampleCalendar = new Calendar();
        for (CalendarEntry calendarEntry : getSampleCalendarEntry()) {
            sampleCalendar.addCalendarEntry(calendarEntry);
        }
        return sampleCalendar;
    }

}
