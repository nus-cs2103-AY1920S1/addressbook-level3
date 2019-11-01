package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.STAFF_ALICE;
import static seedu.address.testutil.TypicalPersons.STAFF_BENSON;
import static seedu.address.testutil.TypicalPersons.STAFF_CARL;
import static seedu.address.testutil.TypicalPersons.STAFF_DANIEL;
import static seedu.address.testutil.TypicalPersons.STAFF_ELLE;
import static seedu.address.testutil.TypicalPersons.STAFF_FIONA;
import static seedu.address.testutil.TypicalPersons.STAFF_GEORGE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.events.AppointmentBook;
import seedu.address.model.events.Event;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalEvents {

    public static final Event EVENT_ALICE =
            new EventBuilder(ALICE, 0, 0, 0, -1, 0).build();
    public static final Event EVENT_BENSON =
            new EventBuilder(BENSON, 0, 0, 0, 0, -15).build();
    public static final Event EVENT_CARL =
            new EventBuilder(CARL, 0, 0, 0, 0, 0).build();
    public static final Event EVENT_DANIEL =
            new EventBuilder(DANIEL, 0, 0, 0, 0, 0).build();
    public static final Event EVENT_ELLE =
            new EventBuilder(ELLE, 0, 0, 0, 6, 0).build();
    public static final Event EVENT_FIONA =
            new EventBuilder(FIONA, 0, 0, 1, 0, 0).build();
    public static final Event EVENT_GEORGE =
            new EventBuilder(GEORGE, 0, 0, 2, 0, 0).build();

    public static final Event DUTY_ALICE =
            new EventBuilder(EVENT_ALICE).withId(STAFF_ALICE).build();
    public static final Event DUTY_BENSON =
            new EventBuilder(EVENT_BENSON).withId(STAFF_BENSON).build();
    public static final Event DUTY_CARL =
            new EventBuilder(EVENT_CARL).withId(STAFF_CARL).build();
    public static final Event DUTY_DANIEL =
            new EventBuilder(EVENT_DANIEL).withId(STAFF_DANIEL).build();
    public static final Event DUTY_ELLE =
            new EventBuilder(EVENT_ELLE).withId(STAFF_ELLE).build();
    public static final Event DUTY_FIONA =
            new EventBuilder(EVENT_FIONA).withId(STAFF_FIONA).build();
    public static final Event DUTY_GEORGE =
            new EventBuilder(EVENT_GEORGE).withId(STAFF_GEORGE).build();

    // Manually added
    public static final Event EVENT_HOON =
            new EventBuilder(HOON, 0, 0, 0, 0, 15).build();
    public static final Event EVENT_IDA =
            new EventBuilder(IDA, 0, 0, 1, 0, 0).build();

    private TypicalEvents() {
    } // prevents instantiation


    public static List<Event> getTypicalAppointment() {
        return new ArrayList<>(Arrays.asList(
                EVENT_ALICE, EVENT_BENSON, EVENT_CARL, EVENT_DANIEL, EVENT_ELLE, EVENT_FIONA, EVENT_GEORGE));
    }

    public static List<Event> getTypicalDutyRoster() {
        return new ArrayList<>(Arrays.asList(
                DUTY_ALICE, DUTY_BENSON, DUTY_CARL, DUTY_DANIEL, DUTY_ELLE, DUTY_FIONA, DUTY_GEORGE));
    }

    /**
     * Returns an {@code AppointmentBook} with all the typical appointments.
     */
    public static AppointmentBook getTypicalAppointmentBook() {
        AppointmentBook ap = new AppointmentBook();
        for (Event event : getTypicalAppointment()) {
            ap.addEvent(event);
        }
        return ap;
    }

    /**
     * Returns an {@code AppointmentBook} with all the typical duty shift.
     */
    public static AppointmentBook getTypicalDutyRosterBook() {
        AppointmentBook ap = new AppointmentBook();
        for (Event event : getTypicalDutyRoster()) {
            ap.addEvent(event);
        }
        return ap;
    }
}
