package cs.f10.t1.nursetraverse.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs.f10.t1.nursetraverse.model.AppointmentBook;
import cs.f10.t1.nursetraverse.model.appointment.Appointment;
import cs.f10.t1.nursetraverse.model.patient.Patient;

/**
 * A utility class containing a list of {@code Patient} objects to be used in tests.
 */
public class TypicalAppointments {

    private static List<Patient> typicalPatients = TypicalPatients.getTypicalPatients();
    private static int index = 1;
    private static Long zero = Long.parseLong("0");
    private static Long[] frequency = new Long[]{zero, zero, zero, zero, zero, zero};

    public static final Appointment ALICE_APPT = new AppointmentBuilder().withStartDateTime("01-12-2019 1000")
            .withEndDateTime("01-12-2019 1200").withFrequency(frequency).withPatientIndex(index)
            .withPatient(typicalPatients.get(index - 1)).withDescription("Dental checkup").build();

    private TypicalAppointments() {} // prevents instantiation

    /**
     * Returns an {@code AppointmentBook} with all the typical appointments.
     */
    public static AppointmentBook getTypicalAppointmentBook() {
        AppointmentBook ab = new AppointmentBook();
        for (Appointment appointment : getTypicalAppointments()) {
            ab.addAppointment(appointment);
        }
        return ab;
    }

    public static List<Appointment> getTypicalAppointments() {
        return new ArrayList<>(Arrays.asList(ALICE_APPT));
    }
}
