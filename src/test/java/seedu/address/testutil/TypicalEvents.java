package seedu.address.testutil;

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
            new EventBuilder(0, 0, 0, -1, 0).withId("01A").build();
    public static final Event EVENT_BENSON =
            new EventBuilder(0, 0, 0, 0, -15).withId("02B").build();
    public static final Event EVENT_CARL =
            new EventBuilder(0, 0, 0, 0, 0).withId("03C").build();
    public static final Event EVENT_DANIEL =
            new EventBuilder(0, 0, 0, 0, 0).withId("04D").build();
    public static final Event EVENT_ELLE =
            new EventBuilder(0, 0, 0, 6, 0).withId("05E").build();
    public static final Event EVENT_FIONA =
            new EventBuilder(0, 0, 1, 0, 0).withId("06F").build();
    public static final Event EVENT_GEORGE =
            new EventBuilder(0, 0, 2, 0, 0).withId("07G").build();

    public static final Event DUTY_ALICE =
            new EventBuilder(EVENT_ALICE).withId("S01A").build();
    public static final Event DUTY_BENSON =
            new EventBuilder(EVENT_BENSON).withId("S02B").build();
    public static final Event DUTY_CARL =
            new EventBuilder(EVENT_CARL).withId("S03C").build();
    public static final Event DUTY_DANIEL =
            new EventBuilder(EVENT_DANIEL).withId("S04D").build();
    public static final Event DUTY_ELLE =
            new EventBuilder(EVENT_ELLE).withId("S05E").build();
    public static final Event DUTY_FIONA =
            new EventBuilder(EVENT_FIONA).withId("S06F").build();
    public static final Event DUTY_GEORGE =
            new EventBuilder(EVENT_GEORGE).withId("S07G").build();

    // Manually added
    public static final Event EVENT_HOON =
            new EventBuilder(0, 0, 0, 0, 15).withId("08H").build();
    public static final Event EVENT_IDA =
            new EventBuilder(0, 0, 1, 0, 0).withId("09I").build();

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
