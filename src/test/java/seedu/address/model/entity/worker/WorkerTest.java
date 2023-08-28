package seedu.address.model.entity.worker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.PhoneNumber;
import seedu.address.model.entity.Sex;
import seedu.address.model.entity.UniqueIdentificationNumberMaps;
import seedu.address.model.person.Name;
import seedu.address.testutil.TypicalWorkers;
import seedu.address.testutil.WorkerBuilder;

//@@author shaoyi1997
class WorkerTest {

    @SuppressWarnings("checkstyle:Regexp")
    @Test
    public void isSameWorker() {
        // same object -> returns true
        assertTrue(ALICE.isSameWorker(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameWorker(null));

        // different person entirely -> returns false
        assertFalse(ALICE.isSameWorker(BENSON));

        // different phone -> returns true
        Worker editedAlice = new WorkerBuilder(ALICE).withPhone(WorkerBuilder.DEFAULT_PHONE).build();
        assertTrue(ALICE.isSameWorker(editedAlice));

        // different name -> returns false
        editedAlice = new WorkerBuilder(ALICE).withName(DEFAULT_NAME).build();
        assertFalse(ALICE.isSameWorker(editedAlice));

        // same identity fields, different data attributes -> returns true
        editedAlice = new WorkerBuilder(ALICE).withDateOfBirth(WorkerBuilder.DEFAULT_DATE_OF_BIRTH)
            .withEmploymentStatus(WorkerBuilder.DEFAULT_EMPLOYMENT_STATUS).build();
        assertTrue(ALICE.isSameWorker(editedAlice));
    }

    //@@author ambervoong
    @Test
    void generateNewStoredWorker_correctParameters_success() throws ParseException {
        Name name = new Name(DEFAULT_NAME);
        Date dateJoined = ParserUtil.parseDate("01/11/2019");
        Worker actual = Worker.generateNewStoredWorker(name, Sex.MALE, dateJoined, 5);
        assertEquals(IdentificationNumber.customGenerateId("W", 5), actual.getIdNum());
        assertEquals(name, actual.getName());
        assertEquals(Optional.empty(), actual.getPhone());
    }

    @Test
    void generateNewStoredBody_wrongParameters_failure() throws ParseException {
        Name name = new Name(DEFAULT_NAME);
        Date dateJoined = ParserUtil.parseDate("01/11/2019");
        assertThrows(IllegalArgumentException.class, () -> Worker.generateNewStoredWorker(name,
                Sex.MALE, dateJoined, -1));
    }
    //@@author

    @Test
    public void equals() {
        Worker aliceInstance = new WorkerBuilder(TypicalWorkers.ALICE).build();
        // same values -> returns true
        Worker aliceCopy = new WorkerBuilder(ALICE).build();
        assertTrue(aliceInstance.equals(aliceCopy));

        // same object -> returns true
        assertTrue(aliceInstance.equals(aliceCopy));

        // same hashcode -> returns true
        assertEquals(aliceInstance.hashCode(), aliceCopy.hashCode());

        // null -> returns false
        assertFalse(aliceInstance.equals(null));

        // different type -> returns false
        assertFalse(aliceInstance.equals(5));

        // different person -> returns false
        assertFalse(aliceInstance.equals(BENSON));

        // different name -> returns false
        Worker editedAlice = new WorkerBuilder(ALICE).withName(DEFAULT_NAME).build();
        assertFalse(aliceInstance.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new WorkerBuilder(ALICE).withPhone(WorkerBuilder.DEFAULT_PHONE).build();
        assertFalse(aliceInstance.equals(editedAlice));

        // different sex -> returns false
        editedAlice = new WorkerBuilder(ALICE).withSex(WorkerBuilder.DEFAULT_SEX).build();
        assertFalse(aliceInstance.equals(editedAlice));
    }

    @Test
    void gettersAndSetters_correct() throws ParseException {
        UniqueIdentificationNumberMaps.clearAllEntries();
        assertEquals(IdentificationNumber.customGenerateId("W", 1),
                new WorkerBuilder().build().getIdNum());
        Worker testWorker = new WorkerBuilder().build();

        // Name
        assertEquals(new Name(DEFAULT_NAME), testWorker.getName());

        // Date
        Date newDate = ParserUtil.parseDate("2/2/2000");
        testWorker.setDateJoined(newDate);
        assertEquals(newDate, testWorker.getDateJoined());
        testWorker.setDateOfBirth(newDate);
        assertEquals(newDate, testWorker.getDateOfBirth().get());

        // Designation
        testWorker.setDesignation("manager");
        assertEquals("manager", testWorker.getDesignation().get());

        // Phone number
        PhoneNumber newPhone = new PhoneNumber("90000001");
        testWorker.setPhone(newPhone);
        assertEquals(newPhone, testWorker.getPhone().get());

        // Sex
        testWorker.setSex(FEMALE);
        assertEquals(FEMALE, testWorker.getSex());

        // Employment status
        testWorker.setEmploymentStatus("Test status");
        assertEquals("Test status", testWorker.getEmploymentStatus().get());
    }

    @Test
    void toString_correct() throws ParseException {
        UniqueIdentificationNumberMaps.clearAllEntries();
        assertEquals(DEFAULT_NAME + " Worker ID: W00001" + " Sex: " + DEFAULT_SEX + " Phone: " + DEFAULT_PHONE
                + " Date of Birth: " + ParserUtil.parseDate(DEFAULT_DATE_OF_BIRTH)
                + " Date Joined: " + ParserUtil.parseDate(DEFAULT_DATE_JOINED)
                + " Designation: " + DEFAULT_DESIGNATION + " Employment Status: " + DEFAULT_EMPLOYMENT_STATUS,
                new WorkerBuilder().build().toString());
    }
}
//@@author
