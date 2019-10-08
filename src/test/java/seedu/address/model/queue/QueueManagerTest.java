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

        queueManager.addPatient(patient);
        queueManager.addRoom(doctor);
        queueManager.serveNext(0);
        assertEquals(0, queueManager.getSizeOfQueue());
    }

    @Test
    void serveNext_index_success() {
        queueManager = new QueueManager();
        Person patient = new PersonBuilder(AMY).build();
        Person doctor = new PersonBuilder(BOB).build();

        queueManager.addPatient(patient);
        queueManager.addRoom(doctor);
        queueManager.serveNext(0);
        assertEquals(0, queueManager.getSizeOfQueue());
    }

    @Test
    void serve_failure() {
        queueManager = new QueueManager();
        Person patient = new PersonBuilder(AMY).build();
        Person doctor = new PersonBuilder(BOB).build();
        Person patient2 = new PersonBuilder(CARL).build();
        queueManager.addPatient(patient);
        queueManager.addRoom(doctor);
        queueManager.addPatient(patient2);
        queueManager.serveNext(0);
        assertEquals(1, queueManager.getSizeOfQueue());
    }

    @Test
    void addPatient_success() {
        queueManager = new QueueManager();
        Person patient = new PersonBuilder(AMY).build();
        Person doctor = new PersonBuilder(BOB).build();
        queueManager.addPatient(patient);
        assertEquals(1, queueManager.getSizeOfQueue());
    }

    @Test
    void removePatient_success() {
        queueManager = new QueueManager();
        Person patient = new PersonBuilder(AMY).build();
        queueManager.addPatient(patient);
        queueManager.removePatient(patient);
        assertEquals(0, queueManager.getSizeOfQueue());
    }

    @Test
    void removePatient_usingIndex_success() {
        queueManager = new QueueManager();
        Person patient = new PersonBuilder(AMY).build();
        queueManager.addPatient(patient);
        queueManager.removePatient(0);
        assertEquals(0, queueManager.getSizeOfQueue());
    }

    @Test
    void poll_success() {
        queueManager = new QueueManager();
        Person patient = new PersonBuilder(AMY).build();
        queueManager.addPatient(patient);
        queueManager.poll();
        assertEquals(0, queueManager.getSizeOfQueue());
    }

    @Test
    void hasPatient_success() {
        queueManager = new QueueManager();
        Person patient = new PersonBuilder(AMY).build();
        queueManager.addPatient(patient);
        assertEquals(true, queueManager.hasPatient(patient));
    }

    @Test
    void getCurrentlyServed_success() {
        queueManager = new QueueManager();
        Person patient = new PersonBuilder(AMY).build();
        Person doctor = new PersonBuilder(BOB).build();

        queueManager.addPatient(patient);
        queueManager.addRoom(doctor);
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
        queueManager.addPatient(patient);
        queueManager2.addPatient(patient);
        queueManager.addPatient(patient2);
        queueManager2.addPatient(patient2);
        queueManager.addRoom(doctor);
        queueManager2.addRoom(doctor);
        queueManager.addRoom(doctor2);
        queueManager2.addRoom(doctor2);
        queueManager.removeRoom(0);
        queueManager2.removeRoom(0);
        assertEquals(true, queueManager.equals(queueManager2));
    }
}