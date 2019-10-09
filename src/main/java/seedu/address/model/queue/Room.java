package seedu.address.model.queue;

import seedu.address.model.common.ReferenceId;

public class Room {
    private final ReferenceId doctor;
    private final ReferenceId patientCurrentlyBeingServed;

    public Room(ReferenceId doctor, ReferenceId patient) {
        this.doctor = doctor;
        this.patientCurrentlyBeingServed = patient;
    }

    public boolean isReadyToServe() {
        return patientCurrentlyBeingServed == null;
    }

    public ReferenceId getDoctor() {
        return doctor;
    }

    public ReferenceId getCurrentPatient() {
        return patientCurrentlyBeingServed;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Room // instanceof handles nulls
            && doctor.equals(((Room) other).doctor));
    }
}
