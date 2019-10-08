package seedu.address.model.queue;

import seedu.address.model.person.Person;

public class Room {
    private Person patientCurrentlyBeingServed;
    private Person doctor;
    private boolean isReady;

    public Room(Person person) {
        this.doctor = person;
        isReady = true;
    }

    public boolean isReadyToServe() {
        return isReady;
    }

    public void serve(Person person) {
        patientCurrentlyBeingServed = person;
        isReady = false;
    }

    public void done() {
        patientCurrentlyBeingServed = null;
        isReady = true;
    }

    public Person getPatientCurrentlyBeingServed() {
        return patientCurrentlyBeingServed;
    }

    public Person getDoctor() {
        return doctor;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Room // instanceof handles nulls
                && doctor.equals(((Room) other).doctor)
                && isReady == ((Room) other).isReady);
    }
}
