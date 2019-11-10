package seedu.address.model.util;

import static seedu.address.model.util.SamplePersonDataUtil.getSamplePersons;
import static seedu.address.model.util.SamplePersonDataUtil.getSampleStaffMember;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import seedu.address.model.ReadOnlyAppointmentBook;
import seedu.address.model.events.AppointmentBook;
import seedu.address.model.events.Event;
import seedu.address.model.events.parameters.DateTime;
import seedu.address.model.events.parameters.Status;
import seedu.address.model.events.parameters.Timing;
import seedu.address.model.person.Person;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleAppointmentDataUtil {

    public static final DateTime ONE_MONTH_LATER_MORNING = new DateTime(LocalDateTime
            .of(LocalDate.now(), LocalTime.of(9, 0)).plusDays(31));

    public static final DateTime ONE_MONTH_LATER_MORNING_PLUS = new DateTime(LocalDateTime
            .of(LocalDate.now(), LocalTime.of(10, 0)).plusDays(31));

    public static final DateTime ONE_MONTH_LATER_MORNING_CHANGE = new DateTime(LocalDateTime
            .of(LocalDate.now(), LocalTime.of(13, 0)).plusDays(31));

    public static final DateTime ONE_MONTH_LATER_MORNING_PLUS_CHANGE = new DateTime(LocalDateTime
            .of(LocalDate.now(), LocalTime.of(14, 0)).plusDays(31));

    public static final DateTime ONE_MONTH_LATER_AFTERNOON = new DateTime(LocalDateTime
            .of(LocalDate.now(), LocalTime.of(12, 0)).plusDays(31));

    public static final DateTime ONE_MONTH_LATER_EVENING = new DateTime(LocalDateTime
            .of(LocalDate.now(), LocalTime.of(21, 0)).plusDays(31));

    private static Event generateEvent(Person person, LocalDateTime localDateTime, Status status) {
        return new Event(person.getReferenceId(), person.getName(), new Timing(new DateTime(localDateTime)), status);
    }

    public static Event[] getSampleAppointments() {
        int count = 100;
        Event[] listOfEvents = new Event[count];
        Status status = new Status();
        LocalDateTime startLocalDateTime = LocalDateTime
                .of(LocalDate.now(), LocalTime.of(9, 0)).minusDays(5);

        Person[] samplePerson = getSamplePersons();

        for (int i = 0; i < count; i++) {
            if (i % 5 == 0) {
                startLocalDateTime = startLocalDateTime.withHour(9).plusDays(1);
            } else {
                startLocalDateTime = startLocalDateTime.plusHours(1);
            }

            listOfEvents[i] = generateEvent(samplePerson[i % 10], startLocalDateTime, status);
        }
        return listOfEvents;
    }

    public static Event[] getSampleDutyShifts() {
        int days = 1000;
        Event[] listOfEvents = new Event[days * 3];

        LocalDateTime startLocalDateTime = LocalDateTime
                .of(LocalDate.now(), LocalTime.of(9, 0)).minusDays(10);
        LocalDateTime endLocalDateTime = LocalDateTime
                .of(LocalDate.now(), LocalTime.of(21, 0)).minusDays(10);
        Status status = new Status();


        Person[] sampleStaff = getSampleStaffMember();

        int i = 0;
        while (i < days * 3) {
            startLocalDateTime = startLocalDateTime.plusDays(1);
            endLocalDateTime = endLocalDateTime.plusDays(1);
            Timing workTiming = new Timing(new DateTime(startLocalDateTime), new DateTime(endLocalDateTime));

            listOfEvents[i++] = new Event(sampleStaff[0].getReferenceId(),
                    sampleStaff[0].getName(), workTiming, status);
            listOfEvents[i++] = new Event(sampleStaff[1].getReferenceId(),
                    sampleStaff[1].getName(), workTiming, status);
            listOfEvents[i++] = new Event(sampleStaff[2].getReferenceId(),
                    sampleStaff[2].getName(), workTiming, status);
        }
        return listOfEvents;
    }

    public static ReadOnlyAppointmentBook getSampleAppointmentBook() {
        AppointmentBook sampleAp = new AppointmentBook();
        for (Event sampleEvent : getSampleAppointments()) {
            sampleAp.addEvent(sampleEvent);
        }
        return sampleAp;
    }

    public static ReadOnlyAppointmentBook getSampleDutyRosterBook() {
        AppointmentBook sampleAp = new AppointmentBook();
        for (Event sampleEvent : getSampleDutyShifts()) {
            sampleAp.addEvent(sampleEvent);
        }
        return sampleAp;
    }
}
