package seedu.address.model.util;

import seedu.address.model.ReadOnlyAppointmentBook;
import seedu.address.model.events.AppointmentBook;
import seedu.address.model.events.Event;
import seedu.address.model.events.parameters.DateTime;
import seedu.address.model.events.parameters.Status;
import seedu.address.model.events.parameters.Timing;
import seedu.address.model.person.parameters.PatientReferenceId;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleAppointmentDataUtil {
    private static DateTime toDateTime(String dateTime) {
        return DateTime.tryParseSimpleDateFormat(dateTime);
    }

    public static Event[] getSampleAppointments() {
        return new Event[] {
            new Event(new PatientReferenceId("001A"),
                new Timing(toDateTime("20/01/20 1200"), toDateTime("20/01/20 1230")), new Status()),
            new Event(new PatientReferenceId("002B"),
                new Timing(toDateTime("20/01/20 1200"), toDateTime("20/01/20 1230")), new Status()),
            new Event(new PatientReferenceId("003C"),
                new Timing(toDateTime("20/01/20 1300"), toDateTime("20/01/20 1430")), new Status()),
            new Event(new PatientReferenceId("004D"),
                new Timing(toDateTime("20/01/20 1300"), toDateTime("20/01/20 1330")), new Status())
        };
    }

    public static Event[] getSampleDutyShifts() {
        return new Event[] {
                new Event(new PatientReferenceId("001A"),
                        new Timing(toDateTime("20/01/20 1200"), toDateTime("20/01/20 1230")), new Status()),
                new Event(new PatientReferenceId("002B"),
                        new Timing(toDateTime("20/01/20 1200"), toDateTime("20/01/20 1230")), new Status()),
                new Event(new PatientReferenceId("003C"),
                        new Timing(toDateTime("20/01/20 1300"), toDateTime("20/01/20 1430")), new Status()),
                new Event(new PatientReferenceId("004D"),
                        new Timing(toDateTime("20/01/20 1300"), toDateTime("20/01/20 1330")), new Status())
        };
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
