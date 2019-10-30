package seedu.address.model.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import seedu.address.model.ReadOnlyAppointmentBook;
import seedu.address.model.events.AppointmentBook;
import seedu.address.model.events.Event;
import seedu.address.model.events.parameters.DateTime;
import seedu.address.model.events.parameters.Status;
import seedu.address.model.events.parameters.Timing;
import seedu.address.model.person.parameters.PatientReferenceId;
import seedu.address.model.person.parameters.StaffReferenceId;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleAppointmentDataUtil {
    private static DateTime toDateTime(String dateTime) {
        return DateTime.tryParseSimpleDateFormat(dateTime);
    }

    public static Event[] getSampleAppointments() {
        int count = 1000;
        Event[] listOfEvents = new Event[count];
        DateTime currentTIme = DateTime.now();
        DateTime[] listOfStartTimes = new DateTime[10];
        DateTime[] listOfEndTimes = new DateTime[10];
        for (int a = 0; a < 10; a++) {
            listOfStartTimes[a] = currentTIme;
            listOfEndTimes[a] = DateTime.plusHalfHour(listOfStartTimes[a]);
            for (int b = 0; b < a; b++) {
                listOfStartTimes[a] = DateTime.plusHalfHour(listOfStartTimes[a]);
                listOfEndTimes[a] = DateTime.plusHalfHour(listOfStartTimes[a]);
            }
        }

        for (int i = 0; i < count; i++) {
            int j = i % 10;
            if (j == 0 && i > 0) {
                for (int c = 0; c < 10; c++) {
                    listOfStartTimes[c] = DateTime.plusOneDay(listOfStartTimes[c]);
                    listOfEndTimes[c] = DateTime.plusOneDay(listOfStartTimes[c]);
                }
            }
            if (i % 10 == 0) {
                listOfEvents[i] = new Event(new PatientReferenceId(String.format("%04d%s", i, "1A")),
                        new Timing(listOfStartTimes[j], listOfEndTimes[j]), new Status());
            } else if (i % 10 == 1) {
                listOfEvents[i] = new Event(new PatientReferenceId(String.format("%04d%s", i, "2B")),
                        new Timing(listOfStartTimes[j], listOfEndTimes[j]), new Status());
            } else if (i % 10 == 2) {
                listOfEvents[i] = new Event(new PatientReferenceId(String.format("%04d%s", i, "3C")),
                        new Timing(listOfStartTimes[j], listOfEndTimes[j]), new Status());
            } else if (i % 10 == 3) {
                listOfEvents[i] = new Event(new PatientReferenceId(String.format("%04d%s", i, "4D")),
                        new Timing(listOfStartTimes[j], listOfEndTimes[j]), new Status());
            } else if (i % 10 == 4) {
                listOfEvents[i] = new Event(new PatientReferenceId(String.format("%04d%s", i, "5E")),
                        new Timing(listOfStartTimes[j], listOfEndTimes[j]), new Status());
            } else if (i % 10 == 5) {
                listOfEvents[i] = new Event(new PatientReferenceId(String.format("%04d%s", i, "6F")),
                        new Timing(listOfStartTimes[j], listOfEndTimes[j]), new Status());
            } else if (i % 10 == 6) {
                listOfEvents[i] = new Event(new PatientReferenceId(String.format("%04d%s", i, "7G")),
                        new Timing(listOfStartTimes[j], listOfEndTimes[j]), new Status());
            } else if (i % 10 == 7) {
                listOfEvents[i] = new Event(new PatientReferenceId(String.format("%04d%s", i, "8H")),
                        new Timing(listOfStartTimes[j], listOfEndTimes[j]), new Status());
            } else if (i % 10 == 8) {
                listOfEvents[i] = new Event(new PatientReferenceId(String.format("%04d%s", i, "9I")),
                        new Timing(listOfStartTimes[j], listOfEndTimes[j]), new Status());
            } else if (i % 10 == 9) {
                listOfEvents[i] = new Event(new PatientReferenceId(String.format("%04d%s", i, "0J")),
                        new Timing(listOfStartTimes[j], listOfEndTimes[j]), new Status());
            }
        }
        return listOfEvents;
    }

    public static Event[] getSampleDutyShifts() {
        int count = 1000;
        Event[] listOfEvents = new Event[count];
        DateTime[] listOfStartTimes = new DateTime[10];
        DateTime[] listOfEndTimes = new DateTime[10];
        for (int a = 0; a < 10; a++) {
            listOfStartTimes[a] = new DateTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0)));
            listOfEndTimes[a] = listOfStartTimes[a];
            for (int b = 0; b < 18; b++) {
                listOfEndTimes[a] = DateTime.plusHalfHour(listOfEndTimes[a]);
            }
        }

        for (int i = 0; i < count; i++) {
            int j = i % 10;
            if (j == 0 && i > 0) {
                for (int c = 0; c < 10; c++) {
                    listOfStartTimes[c] = DateTime.plusOneDay(listOfStartTimes[c]);
                    listOfEndTimes[c] = DateTime.plusOneDay(listOfStartTimes[c]);
                }
            }
            if (i % 10 == 0) {
                listOfEvents[i] = new Event(new StaffReferenceId("S001A"),
                        new Timing(listOfStartTimes[j], listOfEndTimes[j]), new Status());
            } else if (i % 10 == 1) {
                listOfEvents[i] = new Event(new StaffReferenceId("S002B"),
                        new Timing(listOfStartTimes[j], listOfEndTimes[j]), new Status());
            } else if (i % 10 == 2) {
                listOfEvents[i] = new Event(new StaffReferenceId("S003C"),
                        new Timing(listOfStartTimes[j], listOfEndTimes[j]), new Status());
            } else if (i % 10 == 3) {
                listOfEvents[i] = new Event(new StaffReferenceId("S004D"),
                        new Timing(listOfStartTimes[j], listOfEndTimes[j]), new Status());
            } else if (i % 10 == 4) {
                listOfEvents[i] = new Event(new StaffReferenceId("S005E"),
                        new Timing(listOfStartTimes[j], listOfEndTimes[j]), new Status());
            } else if (i % 10 == 5) {
                listOfEvents[i] = new Event(new StaffReferenceId("S006F"),
                        new Timing(listOfStartTimes[j], listOfEndTimes[j]), new Status());
            } else if (i % 10 == 6) {
                listOfEvents[i] = new Event(new StaffReferenceId("S007G"),
                        new Timing(listOfStartTimes[j], listOfEndTimes[j]), new Status());
            } else if (i % 10 == 7) {
                listOfEvents[i] = new Event(new StaffReferenceId("S008H"),
                        new Timing(listOfStartTimes[j], listOfEndTimes[j]), new Status());
            } else if (i % 10 == 8) {
                listOfEvents[i] = new Event(new StaffReferenceId("S009I"),
                        new Timing(listOfStartTimes[j], listOfEndTimes[j]), new Status());
            } else if (i % 10 == 9) {
                listOfEvents[i] = new Event(new StaffReferenceId("S000J"),
                        new Timing(listOfStartTimes[j], listOfEndTimes[j]), new Status());
            }
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
