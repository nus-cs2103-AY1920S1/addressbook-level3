package seedu.address.model.queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.model.person.parameters.StaffReferenceId;
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

    @Test
    void addRoom_success() {
        Person doctor = new PersonBuilder(BOB).withId("002A").build();
        Person doctor2 = new PersonBuilder(AMY).withId("001A").build();
        queueManager = new QueueManager();
        queueManager.addRoom(doctor.getReferenceId());
        assertEquals(new StaffReferenceId("002A"), queueManager.getRoomList().get(0).getDoctor());
        queueManager.addRoomToIndex(doctor2.getReferenceId(), 0);
        assertEquals(new StaffReferenceId("001A"), queueManager.getRoomList().get(0).getDoctor());
    }

    @Test
    void hasRoom_success() {
        Person doctor = new PersonBuilder(BOB).withId("002A").build();
        queueManager = new QueueManager();
        assertFalse(queueManager.hasRoom(doctor.getReferenceId()));
        queueManager.addRoom(doctor.getReferenceId());
        assertTrue(queueManager.hasRoom(doctor.getReferenceId()));
    }

    @Test
    void equals() {
        queueManager = new QueueManager();

        assertTrue(queueManager.equals(queueManager));

        QueueManager queueManagerCopy = new QueueManager(queueManager);

        assertTrue(queueManager.equals(queueManagerCopy));

        Person patient = new PersonBuilder(AMY).withId("001A").build();
        Person doctor = new PersonBuilder(BOB).withId("002A").build();
        queueManager.addPatient(0, patient.getReferenceId());

        assertFalse(queueManager.equals(queueManagerCopy));

        queueManagerCopy.addRoom(doctor.getReferenceId());
        assertFalse(queueManager.equals(queueManagerCopy));

        queueManagerCopy.addPatient(0, patient.getReferenceId());
        assertFalse(queueManager.equals(queueManagerCopy));
        queueManager.addRoom(doctor.getReferenceId());
        assertTrue(queueManager.equals(queueManagerCopy));
    }
}
