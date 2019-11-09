package cs.f10.t1.nursetraverse.testutil;

import cs.f10.t1.nursetraverse.commons.core.index.Index;
import cs.f10.t1.nursetraverse.model.appointment.Appointment;
import cs.f10.t1.nursetraverse.model.datetime.EndDateTime;
import cs.f10.t1.nursetraverse.model.datetime.RecurringDateTime;
import cs.f10.t1.nursetraverse.model.datetime.StartDateTime;
import cs.f10.t1.nursetraverse.model.patient.Patient;

/**
 * A utility class to help with building Appointment objects.
 */
public class AppointmentBuilder {

    public static final String DEFAULT_START_DATE_TIME = "01-12-2019 1000";
    public static final String DEFAULT_END_DATE_TIME = "01-12-2019 1200";
    public static final Long[] DEFAULT_FREQUENCY = new Long[]{getZero(), getZero(), getZero(), getZero(), getZero(),
                                                              getZero()};
    public static final int DEFAULT_PATIENT_INDEX = 1;
    public static final Patient DEFAULT_PATIENT = TypicalPatients.getTypicalPatients().get(DEFAULT_PATIENT_INDEX - 1);
    public static final String DEFAULT_DESCRIPTION = "Dental checkup";

    private StartDateTime startDateTime;
    private EndDateTime endDateTime;
    private RecurringDateTime frequency;
    private Index patientIndex;
    private Patient patient;
    private String description;

    public AppointmentBuilder() {
        startDateTime = new StartDateTime(DEFAULT_START_DATE_TIME);
        endDateTime = new EndDateTime(DEFAULT_END_DATE_TIME);
        frequency = new RecurringDateTime(DEFAULT_FREQUENCY);
        patientIndex = Index.fromOneBased(DEFAULT_PATIENT_INDEX);
        patient = DEFAULT_PATIENT;
        description = DEFAULT_DESCRIPTION;
    }

    /**
     * Initializes the AppointmentBuilder with the data of {@code appointmentToCopy}.
     */
    public AppointmentBuilder(Appointment appointmentToCopy) {
        startDateTime = appointmentToCopy.getStartDateTime();
        endDateTime = appointmentToCopy.getEndDateTime();
        frequency = appointmentToCopy.getFrequency();
        patientIndex = appointmentToCopy.getPatientIndex();
        patient = appointmentToCopy.getPatient();
        description = appointmentToCopy.getDescription();
    }

    private static Long getZero() {
        Long zero = Long.parseLong("0");
        return zero;
    }

    /**
     * Sets the {@code startDateTime} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withStartDateTime(String startDateTime) {
        this.startDateTime = new StartDateTime(startDateTime);
        return this;
    }

    /**
     * Sets the {@code endDateTime} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withEndDateTime(String endDateTime) {
        this.endDateTime = new EndDateTime(endDateTime);
        return this;
    }

    /**
     * Sets the {@code patientIndex} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withFrequency(Long[] frequency) {
        this.frequency = new RecurringDateTime(frequency);
        return this;
    }

    /**
     * Sets the {@code patientIndex} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withPatientIndex(int patientIndex) {
        this.patientIndex = Index.fromOneBased(patientIndex);
        return this;
    }

    /**
     * Sets the {@code patient} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withPatient(Patient patient) {
        this.patient = patient;
        return this;
    }

    /**
     * Sets the {@code description} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Builds and returns an Appointment based on the functions called on this AppointmentBuilder object prior.
     */
    public Appointment build() {
        Appointment appointment = new Appointment(startDateTime, endDateTime, frequency, patientIndex, description);
        appointment.setPatient(patient);
        return appointment;
    }

}

