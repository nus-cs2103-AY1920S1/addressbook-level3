package seedu.address.model.queue;

import java.util.Optional;

import seedu.address.model.common.ReferenceId;

import static java.util.Objects.requireNonNull;

/**
 * Represents a consultation room involving a single doctor and an optional patient.
 * Guarantees: Reference Id to a doctor is immutable and validated.
 */
public class Room {
    private final ReferenceId doctor;
    private final Optional<ReferenceId> patientCurrentlyBeingServed;

    public Room(ReferenceId doctor, Optional<ReferenceId> patient) {
        this.doctor = doctor;
        this.patientCurrentlyBeingServed = patient;
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

    /**
     * Returns true if both rooms are occupied by the same staff.
     * This defines a weaker notion of equality between two consultation rooms.
     */
    public boolean isSameRoom(Room other) {
        requireNonNull(other);
        return other == this // short circuit if same object
            || doctor.equals(((Room) other).doctor);
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
        return getCurrentPatient().isPresent() == o.getCurrentPatient().isPresent()
                && (getCurrentPatient().isEmpty()
                    || getCurrentPatient().get().equals(o.getCurrentPatient().get()));
    }
}
