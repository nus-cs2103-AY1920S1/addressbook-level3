package seedu.address.model.queue;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

class QueueManagerTest {

    private QueueManager queueManager;

    @Test
    void serveNext_success() {
        queueManager = new QueueManager();
        Person patient = new PersonBuilder(AMY).build();
        Person doctor = new PersonBuilder(BOB).build();

        queueManager.addPatient(patient.getReferenceId());
        queueManager.addRoom(doctor.getReferenceId());
        queueManager.serveNext(0);
        assertEquals(0, queueManager.getSizeOfQueue());
    }

    @Test
    void serveNext_index_success() {
        queueManager = new QueueManager();
        Person patient = new PersonBuilder(AMY).build();
        Person doctor = new PersonBuilder(BOB).build();

        queueManager.addPatient(patient.getReferenceId());
        queueManager.addRoom(doctor.getReferenceId());
        queueManager.serveNext(0);
        assertEquals(0, queueManager.getSizeOfQueue());
    }

    @Test
    void serve_failure() {
        queueManager = new QueueManager();
        Person patient = new PersonBuilder(AMY).build();
        Person doctor = new PersonBuilder(BOB).build();
        Person patient2 = new PersonBuilder(CARL).build();
        queueManager.addPatient(patient.getReferenceId());
        queueManager.addRoom(doctor.getReferenceId());
        queueManager.addPatient(patient2.getReferenceId());
        queueManager.serveNext(0);
        assertEquals(1, queueManager.getSizeOfQueue());
    }

    @Test
    void addPatient_success() {
        queueManager = new QueueManager();
        Person patient = new PersonBuilder(AMY).build();
        Person doctor = new PersonBuilder(BOB).build();
        queueManager.addPatient(patient.getReferenceId());
        assertEquals(1, queueManager.getSizeOfQueue());
    }

    @Test
    void removePatient_success() {
        queueManager = new QueueManager();
        Person patient = new PersonBuilder(AMY).build();
        queueManager.addPatient(patient.getReferenceId());
        queueManager.removePatient(patient.getReferenceId());
        assertEquals(0, queueManager.getSizeOfQueue());
    }

    @Test
    void removePatient_usingIndex_success() {
        queueManager = new QueueManager();
        Person patient = new PersonBuilder(AMY).build();
        queueManager.addPatient(patient.getReferenceId());
        queueManager.removePatient(0);
        assertEquals(0, queueManager.getSizeOfQueue());
    }

    @Test
    void poll_success() {
        queueManager = new QueueManager();
        Person patient = new PersonBuilder(AMY).build();
        queueManager.addPatient(patient.getReferenceId());
        queueManager.poll();
        assertEquals(0, queueManager.getSizeOfQueue());
    }

    @Test
    void hasPatient_success() {
        queueManager = new QueueManager();
        Person patient = new PersonBuilder(AMY).build();
        queueManager.addPatient(patient.getReferenceId());
        assertEquals(true, queueManager.hasId(patient.getReferenceId()));
    }
}
