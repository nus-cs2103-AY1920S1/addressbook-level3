package cs.f10.t1.nursetraverse.model.appointment;

import java.time.LocalDateTime;
import java.util.Objects;

import cs.f10.t1.nursetraverse.commons.core.index.Index;
import cs.f10.t1.nursetraverse.commons.util.CollectionUtil;
import cs.f10.t1.nursetraverse.model.datetime.EndDateTime;
import cs.f10.t1.nursetraverse.model.datetime.RecurringDateTime;
import cs.f10.t1.nursetraverse.model.datetime.StartDateTime;
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
    private Index patientIndex;
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

    public void setPatientIndex(Index patientIndex) {
        this.patientIndex = patientIndex;
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
                && otherAppointment.getEndDateTime().equals(getEndDateTime())
                && otherAppointment.getFrequency().equals(getFrequency())
                && otherAppointment.getPatient().equals(getPatient());
    }

    /**
     * Returns true if both appointments have overlapping start and end date times.
     */
    public boolean isOverlappingTime(Appointment otherAppointment) {
        LocalDateTime otherStart = otherAppointment.getStartDateTime().dateTime;
        LocalDateTime otherEnd = otherAppointment.getEndDateTime().dateTime;

        LocalDateTime thisStart = getStartDateTime().dateTime;
        LocalDateTime thisEnd = getEndDateTime().dateTime;

        // Case 1 when overlapping: otherStart is before or equal to thisStart AND otherEnd is equal or after thisStart
        boolean caseOneOverlapTrue = (otherStart.isBefore(thisStart) || otherStart.isEqual(thisStart))
                                        && (otherEnd.isEqual(thisStart) || otherEnd.isAfter(thisStart));

        // Case 2 when overlapping: otherStart is between or equal to the thisStart and thisEnd
        boolean caseTwoOverlapTrue = otherStart.isEqual(thisStart)
                                        || (otherStart.isAfter(thisStart) && otherStart.isBefore(thisEnd));

        return caseOneOverlapTrue || caseTwoOverlapTrue;

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
                && otherAppointment.getPatientIndex().equals(getPatientIndex())
                && otherAppointment.getPatient().equals(getPatient())
                && otherAppointment.getDescription().equals(getDescription());
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
                .append(" Patient Index: ")
                .append(getPatientIndex())
                .append(" Patient: ")
                .append(getPatient())
                .append(" Description: ")
                .append(getDescription());
        return builder.toString();
    }

}
