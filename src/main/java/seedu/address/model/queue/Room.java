package seedu.address.model.queue;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.model.common.ReferenceId;


/**
 * Represents a consultation room involving a single doctor and an optional patient.
 * Guarantees: Reference Id to a doctor is immutable and validated.
 */
public class Room {
    private final ReferenceId doctor;
    private Optional<ReferenceId> patientCurrentlyBeingServed;
    private boolean isResting;

    public Room(ReferenceId doctor, Optional<ReferenceId> patient) {
        this.doctor = doctor;
        this.patientCurrentlyBeingServed = patient;
        this.isResting = false;
    }

    public Room(ReferenceId doctor) {
        this.doctor = doctor;
        this.patientCurrentlyBeingServed = Optional.empty();
    }

    public boolean isReadyToServe() {
        return patientCurrentlyBeingServed.isEmpty();
    }

    public ReferenceId getDoctor() {
        return doctor;
    }

    public Optional<ReferenceId> getCurrentPatient() {
        return patientCurrentlyBeingServed;
    }

    public void removeCurrentPatient() {
        patientCurrentlyBeingServed = Optional.empty();
    }

    /**
     * Returns true if both rooms are occupied by the same staff.
     * This defines a weaker notion of equality between two consultation rooms.
     */
    public boolean isSameRoom(Room other) {
        requireNonNull(other);
        return other == this // short circuit if same object
            || doctor.equals(((Room) other).doctor);
    }

    public void serve(ReferenceId id) {
        patientCurrentlyBeingServed = Optional.of(id);
    }

    public void rest() {
        isResting = false;
    }

    /**
     * Returns true if both rooms occupied by the same staff and patient.
     * This defines a stronger notion of equality between two consultation rooms.
     */
    @Override
    public boolean equals(Object other) {
        if (other == null || !(other instanceof Room)) {
            return false;
        }

        if (other == this) {
            return true;
        }

        Room o = (Room) other;
        return getDoctor().equals(o.getDoctor())
                && getCurrentPatient().isPresent() == o.getCurrentPatient().isPresent()
                && isResting == o.isResting
                && (getCurrentPatient().isEmpty()
                    || getCurrentPatient().get().equals(o.getCurrentPatient().get()));
    }
}
