package seedu.address.model.entity.worker;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalWorkers.ALICE;
import static seedu.address.testutil.TypicalWorkers.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.WorkerBuilder;

class WorkerTest {

    @SuppressWarnings("checkstyle:Regexp")
    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // different person entirely -> returns false
        assertFalse(ALICE.isSamePerson(BENSON));

        // different phone and email -> returns false
        Worker editedAlice = new WorkerBuilder(ALICE).withPhone(WorkerBuilder.DEFAULT_PHONE).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // different name -> returns false
        editedAlice = new WorkerBuilder(ALICE).withName(WorkerBuilder.DEFAULT_NAME).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new WorkerBuilder(ALICE).withDateJoined(WorkerBuilder.DEFAULT_DATE_JOINED)
            .withDateOfBirth(WorkerBuilder.DEFAULT_DATE_OF_BIRTH)
            .withEmploymentStatus(WorkerBuilder.DEFAULT_EMPLOYMENT_STATUS).build();
        assertTrue(ALICE.isSamePerson(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Worker aliceCopy = new WorkerBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BENSON));

        // different name -> returns false
        Worker editedAlice = new WorkerBuilder(ALICE).withName(WorkerBuilder.DEFAULT_NAME).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new WorkerBuilder(ALICE).withPhone(WorkerBuilder.DEFAULT_PHONE).build();
        assertFalse(ALICE.equals(editedAlice));

        // different sex -> returns false
        editedAlice = new WorkerBuilder(ALICE).withSex(WorkerBuilder.DEFAULT_SEX).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
