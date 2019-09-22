package seedu.address.model.entity.body;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.entity.PhoneNumber;
import seedu.address.model.entity.Sex;
import seedu.address.model.person.Name;

//@@author ambervoong
class BodyTest {

    private Body testBody = new Body(new BodyIdentificationNumberStub("B00000001"), new NameStub("Alice Tan"),
            Sex.FEMALE, "01/01/2019");

    @Test
    void getBodyIdentificationNumber() {
        assertEquals(new BodyIdentificationNumberStub("B00000001"), testBody.getBodyIdentificationNumber());
    }

    @Test
    void getName() {
        assertEquals(new NameStub("Alice Tan"), testBody.getName());
    }

    @Test
    void getSex() {
        assertEquals(Sex.FEMALE, testBody.getSex());
    }

    @Test
    void getDateOfAdmission() {
        assertEquals("01/01/2019", testBody.getDateOfAdmission());
    }

    @Test
    void getSetDateOfBirth() {
        testBody.setDateOfBirth("01/01/2019");
        assertEquals("01/01/2019", testBody.getDateOfBirth());

    }

    @Test
    void getSetDateOfDeath() {
        testBody.setDateOfDeath("01/01/2019");
        assertEquals("01/01/2019", testBody.getDateOfDeath());
    }

    @Test
    void getSetNric() {
        testBody.setNric(new NricStub("S1234567A"));
        assertEquals(new NricStub("S1234567A"), testBody.getNric());

    }

    @Test
    void getSetReligion() {
        testBody.setReligion(Religion.SIKHISM);
        assertEquals(Religion.SIKHISM, testBody.getReligion());
    }

    @Test
    void getSetNextOfKin() {
        testBody.setNextOfKin(new NameStub("Alicia Pong"));
        assertEquals(new NameStub("Alicia Pong"), testBody.getNextOfKin());
    }

    @Test
    void getSetRelationship() {
        testBody.setRelationship(Relationship.FATHER);
        assertEquals(Relationship.FATHER, testBody.getRelationship());
    }

    @Test
    void getSetKinPhoneNumber() {
        testBody.setKinPhoneNumber(new PhoneNumberStub("87871234"));
        assertEquals(new PhoneNumberStub("87871234"), testBody.getKinPhoneNumber());
    }

    @Test
    void getSetCauseOfDeath() {
        testBody.setCauseOfDeath("Gangrene");
        assertEquals("Gangrene", testBody.getCauseOfDeath());
    }

    @Test
    void getSetOrgansForDonation() {
        testBody.setOrgansForDonation(DonationList.LIVER);
        assertEquals(DonationList.LIVER, testBody.getOrgansForDonation());
    }

    @Test
    void getSetStatus() {
        testBody.setStatus(Status.ARRIVED);
        assertEquals(Status.ARRIVED, testBody.getStatus());
    }

    @Test
    void getFridgeId() {
    }

    @Test
    void setFridgeId() {
    }

    @Test
    void getDetails() {
    }

    @Test
    void setDetails() {
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
     * A BodyIdentificationNumber stub that just stores the ID it is given.
     */
    private static class BodyIdentificationNumberStub extends BodyIdentificationNumber {

        public BodyIdentificationNumberStub(String id) {
            super(id);
        }
    }

    /**
     * A PhoneNumber stub that just stores the number it is given.
     */
    private static class PhoneNumberStub extends PhoneNumber {

        public PhoneNumberStub(String number) {
            super(number);
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
