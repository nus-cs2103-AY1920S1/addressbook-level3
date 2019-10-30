package seedu.address.model.util;

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
        return new Event[] {
            new Event(patientRefId("001A"),
                new Timing(toDateTime("20/01/20 1200"), toDateTime("20/01/20 1230")), new Status()),
            new Event(patientRefId("002B"),
                new Timing(toDateTime("20/01/20 1200"), toDateTime("20/01/20 1230")), new Status()),
            new Event(patientRefId("003C"),
                new Timing(toDateTime("20/01/20 1300"), toDateTime("20/01/20 1430")), new Status()),
            new Event(patientRefId("004D"),
                new Timing(toDateTime("20/01/20 1300"), toDateTime("20/01/20 1330")), new Status())
        };
    }

    public static Event[] getSampleDutyShifts() {
        return new Event[] {
                new Event(staffRefId("S001A"),
                        new Timing(toDateTime("20/01/20 1200"), toDateTime("20/01/20 1230")), new Status()),
                new Event(staffRefId("S002B"),
                        new Timing(toDateTime("20/01/20 1200"), toDateTime("20/01/20 1230")), new Status()),
                new Event(staffRefId("S003C"),
                        new Timing(toDateTime("20/01/20 1300"), toDateTime("20/01/20 1430")), new Status()),
                new Event(staffRefId("S004D"),
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
