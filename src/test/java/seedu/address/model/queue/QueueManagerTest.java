package seedu.address.model.queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.HOON;

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

    @Test
    void getCurrentlyServed_success() {
        queueManager = new QueueManager();
        Person patient = new PersonBuilder(AMY).build();
        Person doctor = new PersonBuilder(BOB).build();

        queueManager.addPatient(patient.getReferenceId());
        queueManager.addRoom(doctor.getReferenceId());
        queueManager.serveNext(0);
        assertEquals(patient, queueManager.getCurrentlyServed(0));
    }

    @Test
    void equals_success() {
        queueManager = new QueueManager();
        QueueManager queueManager2 = new QueueManager(queueManager);
        Person patient = new PersonBuilder(AMY).build();
        Person doctor = new PersonBuilder(BOB).build();
        Person patient2 = new PersonBuilder(CARL).build();
        Person doctor2 = new PersonBuilder(HOON).build();
        queueManager.addPatient(patient.getReferenceId());
        queueManager2.addPatient(patient.getReferenceId());
        queueManager.addPatient(patient2.getReferenceId());
        queueManager2.addPatient(patient2.getReferenceId());
        queueManager.addRoom(doctor.getReferenceId());
        queueManager2.addRoom(doctor.getReferenceId());
        queueManager.addRoom(doctor2.getReferenceId());
        queueManager2.addRoom(doctor2.getReferenceId());
        queueManager.removeRoom(0);
        queueManager2.removeRoom(0);
        assertEquals(true, queueManager.equals(queueManager2));
    }
}
