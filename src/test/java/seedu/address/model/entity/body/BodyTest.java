package seedu.address.model.entity.body;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.BodyBuilder.DEFAULT_NAME;
import static seedu.address.testutil.TypicalBodies.ALICE;
import static seedu.address.testutil.TypicalBodies.BOB;
import static seedu.address.testutil.TypicalWorkers.CLARA;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.PhoneNumber;
import seedu.address.model.entity.Sex;
import seedu.address.model.entity.UniqueIdentificationNumberMaps;
import seedu.address.model.entity.fridge.Fridge;
import seedu.address.model.entity.worker.Worker;
import seedu.address.model.person.Name;
import seedu.address.testutil.BodyBuilder;
import seedu.address.testutil.FridgeBuilder;
import seedu.address.testutil.WorkerBuilder;

//@@author ambervoong
class BodyTest {

    @Test
    public void isSameBody() {
        assertTrue(ALICE.isSameBody(ALICE));
        assertFalse(ALICE.isSameBody(null));
        assertFalse(ALICE.isSameBody(BOB));

        // Still equal because only NRIC is considered
        Body editedAlice = new BodyBuilder(ALICE).withSex("FEMALE").build();
        assertTrue(ALICE.isSameBody(editedAlice));

        // Not equal because the NRIC is different.
        editedAlice = new BodyBuilder(ALICE).withNric("F9531049B").build();
        assertFalse(ALICE.isSameBody(editedAlice));
    }

    @Test
    public void isSameBodyIdNum() {
        Body alice = new BodyBuilder(ALICE).build();

        Body differentId = new BodyBuilder(BOB).build(2);
        assertFalse(alice.isSameBodyIdNum(differentId));
    }

    @Test
    public void isSameEntity() {
        Body alice = new BodyBuilder(ALICE).build();
        Body bob = new BodyBuilder(BOB).build(1);
        Worker worker = new WorkerBuilder(CLARA).build();
        assertFalse(alice.isSameEntity(bob));
        // Test equality with non-Body entity.
        assertFalse(alice.isSameEntity(worker));
        assertTrue(alice.isSameEntity(alice));
    }

    @Test
    public void equals() {
        Body aliceCopy = new BodyBuilder(ALICE).build();

        assertTrue(ALICE.equals(ALICE));
        assertEquals(ALICE.hashCode(), aliceCopy.hashCode());
        assertTrue(ALICE.equals(aliceCopy));

        assertFalse(ALICE.equals(null));

        // Different fields
        assertFalse(ALICE.equals(BOB));

        Body editedAlice = new BodyBuilder(ALICE).withName(DEFAULT_NAME).build();
        assertFalse(ALICE.equals(editedAlice));

    }

    // Test factory method
    @Test
    void generateNewStoredBody_correctParameters_success() throws ParseException {
        Date dateOfAdmission = ParserUtil.parseDate("01/11/2019");
        Body actual = Body.generateNewStoredBody(5, dateOfAdmission);
        assertEquals(IdentificationNumber.customGenerateId("B", 5), actual.getIdNum());
        assertEquals(dateOfAdmission, actual.getDateOfAdmission());
        assertEquals(null, actual.getKinPhoneNumber());
    }

    @Test
    void generateNewStoredBody_wrongParameters_failure() throws ParseException {
        Date dateOfAdmission = ParserUtil.parseDate("01/11/2019");
        assertThrows(IllegalArgumentException.class, () -> Body.generateNewStoredBody(-1, dateOfAdmission));
    }

    @Test
    void getBodyIdNum() {
        // Final field; does not have a setter method.
        UniqueIdentificationNumberMaps.clearAllEntries();
        assertEquals(IdentificationNumber.customGenerateId("B", 1),
                new BodyBuilder().build().getIdNum());
    }

    @Test
    void getSetName() {
        ALICE.setName(new Name("Penny"));
        assertEquals(new NameStub("Penny"), ALICE.getName());
    }

    @Test
    void getSetSex() {
        ALICE.setSex(Sex.MALE);
        assertEquals(Sex.MALE, ALICE.getSex());
    }

    @Test
    void getDateOfAdmission() throws ParseException {
        assertEquals(ParserUtil.parseDate("20/10/2019"), ALICE.getDateOfAdmission());
    }

    @Test
    void getSetDateOfBirth() throws ParseException {
        ALICE.setDateOfBirth(ParserUtil.parseDate("01/01/1991"));
        assertEquals(ParserUtil.parseDate("01/01/1991"), ALICE.getDateOfBirth().get());
    }

    @Test
    void getSetDateOfDeath() throws ParseException {
        ALICE.setDateOfDeath(ParserUtil.parseDate("01/01/1991"));
        assertEquals(ParserUtil.parseDate("01/01/1991"), ALICE.getDateOfDeath().get());
    }

    @Test
    void getSetNric() {
        ALICE.setNric(new NricStub("S1234567A"));
        assertEquals(new NricStub("S1234567A"), ALICE.getNric().get());

    }

    @Test
    void getSetReligion() {
        ALICE.setReligion("Islam");
        assertEquals("Islam", ALICE.getReligion().get());
    }

    @Test
    void getSetNextOfKin() {
        ALICE.setNextOfKin(new NameStub("Alicia Pong"));
        assertEquals(new NameStub("Alicia Pong"), ALICE.getNextOfKin().get());
    }

    @Test
    void getSetRelationship() {
        ALICE.setRelationship("Father");
        assertEquals("Father", ALICE.getRelationship().get());
    }

    @Test
    void getSetKinPhoneNumber() {
        ALICE.setKinPhoneNumber(new PhoneNumber("87871234"));
        assertEquals(new PhoneNumber("87871234"), ALICE.getKinPhoneNumber().get());
    }

    @Test
    void getSetCauseOfDeath() {
        ALICE.setCauseOfDeath("Gangrene");
        assertEquals("Gangrene", ALICE.getCauseOfDeath().get());
    }

    @Test
    void getSetOrgansForDonation() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Liver");
        ALICE.setOrgansForDonation(list);
        assertEquals("Liver", ALICE.getOrgansForDonation().get(0));
    }

    @Test
    void getSetStatus() {
        ALICE.setBodyStatus(BodyStatus.ARRIVED);
        assertEquals(BodyStatus.ARRIVED, ALICE.getBodyStatus().get());
    }

    @Test
    void getSetFridgeId() {
        ALICE.setFridgeId(IdentificationNumber.customGenerateId("F", 5));
        assertEquals(IdentificationNumber.customGenerateId("F", 5), ALICE.getFridgeId().get());

        ALICE.setFridgeId(IdentificationNumber.customGenerateId("F", 1));
        // Check that it works with an actual fridge.
        UniqueIdentificationNumberMaps.clearAllEntries();
        Fridge fridge = new FridgeBuilder().build();
        assertEquals(fridge.getIdNum(), new BodyBuilder(ALICE).build().getFridgeId().get());
        ALICE.setFridgeId(null);
    }

    @Test
    void getSetDetails() {
        ALICE.setDetails("note");
        assertEquals("note", ALICE.getDetails().get());
    }

    // Stub Classes
    /**
     * A Name stub that just stores the name it is given.
     */
    private static class NameStub extends Name {

        public NameStub(String name) {
            super(name);
        }
    }

    /**
     * An NRIC stub that just stores the ID it is given.
     */
    private static class NricStub extends Nric {

        public NricStub(String nric) {
            super(nric);
        }
    }
}
