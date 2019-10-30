package seedu.address.model.util;

import java.time.LocalDateTime;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.calendar.Calendar;
import seedu.address.model.calendar.CalendarEntry;
import seedu.address.model.calendar.Description;
import seedu.address.model.calendar.Event;
import seedu.address.model.calendar.Reminder;
import seedu.address.model.calendar.Repetition;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.record.BloodSugarBuilder;
import seedu.address.model.record.BmiBuilder;
import seedu.address.model.record.Record;
import seedu.address.model.record.UniqueRecordList;
import seedu.address.model.time.DateTime;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[]{
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31")),
        };
    }

    public static Record[] getSampleRecords() {
        return new Record[]{
            new BmiBuilder().build(),
            new BmiBuilder().withDateTime("2019-01-02 09:09").withHeight("180.6").withWeight("70.0").build(),
            new BmiBuilder().withDateTime("2019-01-02 09:19").withHeight("179.7").withWeight("69.5").build(),
            new BmiBuilder().withDateTime("2019-02-03 09:29").withHeight("174.8").withWeight("67.0").build(),
            new BmiBuilder().withDateTime("2019-03-04 09:39").withHeight("173.9").withWeight("66.3").build(),
            new BmiBuilder().withDateTime("2019-04-05 09:49").withHeight("172.6").withWeight("65.1").build(),
            new BmiBuilder().withDateTime("2019-05-06 09:59").withHeight("172.6").withWeight("63.2").build(),
            new BmiBuilder().withDateTime("2019-06-07 07:09").withHeight("162.6").withWeight("60.1").build(),
            new BmiBuilder().withDateTime("2019-07-08 06:09").withHeight("152.6").withWeight("55.4").build(),
            new BmiBuilder().withDateTime("2019-08-09 05:09").withHeight("154.6").withWeight("53.3").build(),
            new BmiBuilder().withDateTime("2019-09-10 04:09").withHeight("132.6").withWeight("50.0").build(),
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
            new BloodSugarBuilder().withDateTime("2019-04-03 09:23").withConcentration("5.9").build(),
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

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
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
