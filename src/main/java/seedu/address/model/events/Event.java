package seedu.address.model.events;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.common.DoctorReferenceId;
import seedu.address.model.common.PatientReferenceId;

//TODO: Stub models for now
public abstract class Event {

    // Identity fields
    private final PatientReferenceId patientId;
    private final DoctorReferenceId doctorId;
    private final Timing timing;
    private Status status = Status.NEW;

    /**
     * Every field must be present and not null.
     */
    public Event(PatientReferenceId patientId, DoctorReferenceId doctorId, Timing timing, Status status) {
        requireAllNonNull(patientId, doctorId, timing, status);
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.timing = timing;
        this.status = status;
    }

    public Event(PatientReferenceId patientId, DoctorReferenceId doctorId, Timing timing) {
        requireAllNonNull(patientId, doctorId, timing);
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.timing = timing;
    }

    public PatientReferenceId getPatientId() {
        return patientId;
    }

    public DoctorReferenceId getDoctorId() {
        return doctorId;
    }

    public Timing getEventTiming() {
        return timing;
    }

    public Status getStatus() {
        return status;
    }

    /**
     * Returns true if both Event of the same patient and timing.
     * This defines a weaker notion of equality between two events.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getPatientId().equals(getPatientId())
                && otherEvent.getEventTiming().equals(getEventTiming());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return otherEvent.getPatientId().equals(getPatientId())
                && otherEvent.getDoctorId().equals(getDoctorId())
                && otherEvent.getEventTiming().equals(getEventTiming())
                && otherEvent.getStatus().equals(getStatus());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(patientId, doctorId, timing, status);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Event - Patient: ")
                .append(getPatientId())
                .append(" Timing: ")
                .append(getEventTiming())
                .append(" Doctor: ")
                .append(getDoctorId())
                .append(" status: ")
                .append(getStatus());
        return builder.toString();
    }
}
