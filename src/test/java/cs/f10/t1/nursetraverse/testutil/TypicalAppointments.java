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
    private static int indexOne = 1;
    private static int indexThree = 3;
    private static Long zero = Long.parseLong("0");
    private static Long one = Long.parseLong("1");
    private static Long three = Long.parseLong("3");
    private static Long[] frequencyOne = new Long[]{zero, zero, zero, zero, zero, zero};
    private static Long[] frequencyTwo = new Long[]{one, zero, zero, zero, zero, zero};
    private static Long[] frequencyThree = new Long[]{one, three, zero, zero, zero, zero};

    public static final Appointment APPT_ONE = new AppointmentBuilder().withStartDateTime("01-12-2019 1000")
            .withEndDateTime("01-12-2019 1200").withFrequency(frequencyOne).withPatientIndex(indexOne)
            .withPatient(typicalPatients.get(indexOne - 1)).withDescription("Dental checkup").build();
    public static final Appointment APPT_TWO = new AppointmentBuilder().withStartDateTime("02-12-2019 1000")
            .withEndDateTime("02-12-2019 1200").withFrequency(frequencyTwo).withPatientIndex(indexOne)
            .withPatient(typicalPatients.get(indexOne - 1)).withDescription("Health checkup").build();
    public static final Appointment APPT_THREE = new AppointmentBuilder().withStartDateTime("05-12-2019 1800")
            .withEndDateTime("05-12-2019 1830").withFrequency(frequencyThree).withPatientIndex(indexThree)
            .withPatient(typicalPatients.get(indexThree - 1)).withDescription("Physiotherapy treatment").build();
    public static final Appointment APPT_FOUR = new AppointmentBuilder().withStartDateTime("07-12-2019 1700")
            .withEndDateTime("07-12-2019 1745").withFrequency(frequencyOne).withPatientIndex(indexThree)
            .withPatient(typicalPatients.get(indexThree - 1)).withDescription("").build();
    public static final Appointment APPT_FIVE = new AppointmentBuilder().withStartDateTime("12-12-2019 1200")
            .withEndDateTime("12-12-2019 1300").withFrequency(frequencyTwo).withPatientIndex(indexOne)
            .withPatient(typicalPatients.get(indexOne - 1)).withDescription("").build();

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
        return new ArrayList<>(Arrays.asList(APPT_ONE, APPT_TWO, APPT_THREE, APPT_FOUR, APPT_FIVE));
    }
}
