package cs.f10.t1.nursetraverse.model.appointment;

import java.util.Objects;

import cs.f10.t1.nursetraverse.commons.core.index.Index;
import cs.f10.t1.nursetraverse.commons.util.CollectionUtil;
import cs.f10.t1.nursetraverse.model.datetime.EndDateTime;
import cs.f10.t1.nursetraverse.model.datetime.RecurringDateTime;
import cs.f10.t1.nursetraverse.model.datetime.StartDateTime;
import cs.f10.t1.nursetraverse.model.patient.Address;
import cs.f10.t1.nursetraverse.model.patient.Patient;

/**
 * Represents an Appointment in the application
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Appointment {

    // Identity fields
    private final StartDateTime startDateTime;
    private final EndDateTime endDateTime;
    private final RecurringDateTime frequency;

    // Data fields
    private final Index patientIndex;
    private Patient patient;
    private final String description;

    public Appointment(StartDateTime startDateTime, EndDateTime endDateTime, RecurringDateTime frequency,
                       Index patientIndex, String description) {
        CollectionUtil.requireAllNonNull(startDateTime, endDateTime, frequency, patientIndex, description);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.frequency = frequency;
        this.patientIndex = patientIndex;
        this.description = description;
    }

    public StartDateTime getStartDateTime() {
        return startDateTime;
    }

    public EndDateTime getEndDateTime() {
        return endDateTime;
    }

    public RecurringDateTime getFrequency() {
        return frequency;
    }

    public Index getPatientIndex() {
        return patientIndex;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getDescription() {
        return description;
    }

    public Address getAddress() {
        return patient.getAddress();
    }

    /**
     * Returns true if both appointments of the same date and time have at least one other identity field that is the
     * same.
     * This defines a weaker notion of equality between two appointments.
     */
    public boolean isSameAppointment(Appointment otherAppointment) {
        if (otherAppointment == this) {
            return true;
        }

        return otherAppointment != null
                && otherAppointment.getStartDateTime().equals(getStartDateTime())
                && (otherAppointment.getEndDateTime().equals(getEndDateTime())
                    || otherAppointment.getFrequency().equals(getFrequency()));
    }

    /**
     * Returns true if both appointments have the same identity and data fields.
     * This defines a stronger notion of equality between two appointments.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Appointment)) {
            return false;
        }

        Appointment otherAppointment = (Appointment) other;

        return otherAppointment.getStartDateTime().equals(getStartDateTime())
                && otherAppointment.getEndDateTime().equals(getEndDateTime())
                && otherAppointment.getFrequency().equals(getFrequency())
                && otherAppointment.getPatient().equals(getPatient())
                && otherAppointment.getDescription().equals(getDescription())
                && otherAppointment.getAddress().equals(getAddress());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(startDateTime, endDateTime, frequency, patient, description);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Start Date and Time: ")
                .append(getStartDateTime())
                .append(" End Date and Time ")
                .append(getEndDateTime())
                .append(" Frequency: ")
                .append(getFrequency())
                .append(" Patient: ")
                .append(getPatient())
                .append(" Location: ")
                .append(getAddress())
                .append(" Description: ")
                .append(getDescription());
        return builder.toString();
    }

}
