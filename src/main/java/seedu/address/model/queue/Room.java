package seedu.address.model.queue;

import java.util.Optional;

import seedu.address.model.common.ReferenceId;

/**
 * Represents a consultation room involving a single doctor and an optional patient.
 * Guarantees: Reference Id to a doctor is immutable and validated.
 */
public class Room {
    private final ReferenceId doctor;
    private Optional<ReferenceId> patientCurrentlyBeingServed;

    public Room(ReferenceId doctor, Optional<ReferenceId> patient) {
        this.doctor = doctor;
        this.patientCurrentlyBeingServed = patient;
    }

    public Room(ReferenceId doctor) {
        this.doctor = doctor;
        this.patientCurrentlyBeingServed = null;
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

    public void serve(ReferenceId id) {
        this.patientCurrentlyBeingServed = Optional.of(id);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Room // instanceof handles nulls
                && doctor.equals(((Room) other).doctor));
    }
}
