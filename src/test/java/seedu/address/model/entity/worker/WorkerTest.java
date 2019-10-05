package seedu.address.model.entity.worker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.entity.Sex.FEMALE;
import static seedu.address.testutil.TypicalWorkers.ALICE;
import static seedu.address.testutil.TypicalWorkers.BENSON;
import static seedu.address.testutil.WorkerBuilder.DEFAULT_DATE_JOINED;
import static seedu.address.testutil.WorkerBuilder.DEFAULT_DATE_OF_BIRTH;
import static seedu.address.testutil.WorkerBuilder.DEFAULT_DESIGNATION;
import static seedu.address.testutil.WorkerBuilder.DEFAULT_EMPLOYMENT_STATUS;
import static seedu.address.testutil.WorkerBuilder.DEFAULT_NAME;
import static seedu.address.testutil.WorkerBuilder.DEFAULT_PHONE;
import static seedu.address.testutil.WorkerBuilder.DEFAULT_SEX;

import java.util.Date;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.PhoneNumber;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
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
        editedAlice = new WorkerBuilder(ALICE).withName(DEFAULT_NAME).build();
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

        // same hashcode -> returns true
        assertEquals(ALICE.hashCode(), aliceCopy.hashCode());

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BENSON));

        // different name -> returns false
        Worker editedAlice = new WorkerBuilder(ALICE).withName(DEFAULT_NAME).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new WorkerBuilder(ALICE).withPhone(WorkerBuilder.DEFAULT_PHONE).build();
        assertFalse(ALICE.equals(editedAlice));

        // different sex -> returns false
        editedAlice = new WorkerBuilder(ALICE).withSex(WorkerBuilder.DEFAULT_SEX).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    void gettersAndSetters_correct() throws ParseException {
        assertEquals(IdentificationNumber.customGenerateId("W", 1), ALICE.getWorkerIdNum());
        Worker testWorker = new WorkerBuilder().build();

        assertEquals(new Name(DEFAULT_NAME), testWorker.getName());

        Date newDate = ParserUtil.parseDate("2/2/2000");
        testWorker.setDateJoined(newDate);
        assertEquals(newDate, testWorker.getDateJoined());
        testWorker.setDateOfBirth(newDate);
        assertEquals(newDate, testWorker.getDateOfBirth());

        testWorker.setDesignation("manager");
        assertEquals("manager", testWorker.getDesignation());

        PhoneNumber newPhone = new PhoneNumber("90000001");
        testWorker.setPhone(newPhone);
        assertEquals(newPhone, testWorker.getPhone());

        testWorker.setSex(FEMALE);
        assertEquals(FEMALE, testWorker.getSex());

        testWorker.setEmploymentStatus("Test status");
        assertEquals("Test status", testWorker.getEmploymentStatus());
    }

    @Test
    void toString_correct() throws ParseException {
        assertEquals(DEFAULT_NAME + " Sex: " + DEFAULT_SEX + " Phone: " + DEFAULT_PHONE
                + " Date of Birth: " + ParserUtil.parseDate(DEFAULT_DATE_OF_BIRTH)
                + " Date Joined: " + ParserUtil.parseDate(DEFAULT_DATE_JOINED)
                + " Designation: " + DEFAULT_DESIGNATION + " Employment Status: " + DEFAULT_EMPLOYMENT_STATUS,
                new WorkerBuilder().build().toString());
    }
}
