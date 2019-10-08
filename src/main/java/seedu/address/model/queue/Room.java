package seedu.address.model.queue;

import seedu.address.model.person.Person;

public class Room {
    private final Person doctor;
    private Person patientCurrentlyBeingServed;

    public Room(Person person) {
        this.doctor = person;
    }

    public boolean isReadyToServe() {
        return patientCurrentlyBeingServed == null;
    }

    public void serve(Person person) {
        patientCurrentlyBeingServed = person;
    }

    public void done() {
        patientCurrentlyBeingServed = null;
    }

    public Person getDoctor() {
        return doctor;
    }

    public Person getCurrentPatient() {
        return patientCurrentlyBeingServed;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Room // instanceof handles nulls
            && doctor.equals(((Room) other).doctor));
    }
}
