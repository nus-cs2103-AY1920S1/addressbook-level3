package seedu.address.model.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAppointmentBook;
import seedu.address.model.ReferenceId;
import seedu.address.model.events.AppointmentBook;
import seedu.address.model.events.Event;
import seedu.address.model.events.parameters.DateTime;
import seedu.address.model.events.parameters.Status;
import seedu.address.model.events.parameters.Timing;
import seedu.address.model.person.parameters.PersonReferenceId;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleAppointmentDataUtil {
    private static DateTime toDateTime(String dateTime) {
        return DateTime.tryParseSimpleDateFormat(dateTime);
    }

    /**
     * Parses the {@refId} as a patient's {@code ReferenceId}.
     */
    private static ReferenceId patientRefId(String refId) {
        try {
            return PersonReferenceId.parsePatientReferenceId(refId);
        } catch (ParseException ex) {
            throw new AssertionError("Error should be thrown from sample test data: " + ex.getMessage());
        }
    }

    /**
     * Parses the {@refId} as a staff's {@code ReferenceId}.
     */
    private static ReferenceId staffRefId(String refId) {
        try {
            return PersonReferenceId.parseStaffReferenceId(refId);
        } catch (ParseException ex) {
            throw new AssertionError("Error should be thrown from sample test data: " + ex.getMessage());
        }
    }

    public static Event[] getSampleAppointments() {
        int count = 100;
        Event[] listOfEvents = new Event[count];
        Status status = new Status();
        LocalDateTime startLocalDateTime = LocalDateTime
                .of(LocalDate.now(), LocalTime.of(9, 0)).minusDays(5);

        for (int i = 0; i < count; i++) {
            if (i % 5 == 0) {
                startLocalDateTime = startLocalDateTime.withHour(9).plusDays(1);
            } else {
                startLocalDateTime = startLocalDateTime.plusHours(1);
            }

            if (i % 10 == 0) {
                listOfEvents[i] = new Event(patientRefId(String.format("%04d%s", i, "1A")),
                        new Timing(new DateTime(startLocalDateTime)), status);
            } else if (i % 10 == 1) {
                listOfEvents[i] = new Event(patientRefId(String.format("%04d%s", i, "2B")),
                        new Timing(new DateTime(startLocalDateTime)), status);
            } else if (i % 10 == 2) {
                listOfEvents[i] = new Event(patientRefId(String.format("%04d%s", i, "3C")),
                        new Timing(new DateTime(startLocalDateTime)), status);
            } else if (i % 10 == 3) {
                listOfEvents[i] = new Event(patientRefId(String.format("%04d%s", i, "4D")),
                        new Timing(new DateTime(startLocalDateTime)), status);
            } else if (i % 10 == 4) {
                listOfEvents[i] = new Event(patientRefId(String.format("%04d%s", i, "5E")),
                        new Timing(new DateTime(startLocalDateTime)), status);
            } else if (i % 10 == 5) {
                listOfEvents[i] = new Event(patientRefId(String.format("%04d%s", i, "6F")),
                        new Timing(new DateTime(startLocalDateTime)), status);
            } else if (i % 10 == 6) {
                listOfEvents[i] = new Event(patientRefId(String.format("%04d%s", i, "7G")),
                        new Timing(new DateTime(startLocalDateTime)), status);
            } else if (i % 10 == 7) {
                listOfEvents[i] = new Event(patientRefId(String.format("%04d%s", i, "8H")),
                        new Timing(new DateTime(startLocalDateTime)), status);
            } else if (i % 10 == 8) {
                listOfEvents[i] = new Event(patientRefId(String.format("%04d%s", i, "9I")),
                        new Timing(new DateTime(startLocalDateTime)), status);
            } else if (i % 10 == 9) {
                listOfEvents[i] = new Event(patientRefId(String.format("%04d%s", i, "0J")),
                        new Timing(new DateTime(startLocalDateTime)), status);
            }
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

        int i = 0;
        while (i < days * 3) {
            startLocalDateTime = startLocalDateTime.plusDays(1);
            endLocalDateTime = endLocalDateTime.plusDays(1);
            Timing workTiming = new Timing(new DateTime(startLocalDateTime), new DateTime(endLocalDateTime));

            listOfEvents[i++] = new Event(staffRefId("S001A"), workTiming, status);
            listOfEvents[i++] = new Event(staffRefId("S002B"), workTiming, status);
            listOfEvents[i++] = new Event(staffRefId("S003C"), workTiming, status);
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
