package seedu.address.logic.parser.utility;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalBodies.ALICE;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.PhoneNumber;
import seedu.address.model.entity.Sex;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.body.BodyStatus;
import seedu.address.model.entity.body.Nric;
import seedu.address.model.entity.body.Religion;
import seedu.address.model.person.Name;
import seedu.address.testutil.BodyBuilder;

//@@author ambervoong
class UpdateBodyDescriptorTest {

    @Test
    void isAnyFieldEdited_fieldEdited_true() {
        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor();
        descriptor.setCauseOfDeath("Evisceration");
        assertTrue(descriptor.isAnyFieldEdited());
    }

    @Test
    void isAnyFieldEdited_fieldNotEdited_false() {
        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor();
        assertFalse(descriptor.isAnyFieldEdited());
    }

    @Test
    void apply_fieldsPresent_success() {
        Body body = new BodyBuilder(ALICE).build();
        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor();
        descriptor.setSex(Sex.MALE);
        Body bodyCopy = new BodyBuilder(ALICE).build();
        bodyCopy.setSex(Sex.MALE);
        assertTrue(descriptor.apply(body).equals(bodyCopy));
    }

    @Test
    void apply_allFieldsNotPresent_success() {
        //  Success because apply does not check whether fields are present or not.
        Body body = new BodyBuilder(ALICE).build();
        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor();
        Body bodyCopy = new BodyBuilder(ALICE).build();
        assertTrue(descriptor.apply(body).equals(bodyCopy));
    }

    @Test
    void getSetName() {
        Body body = new BodyBuilder(ALICE).build();
        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor(body);
        descriptor.setName(new Name("Long Tetulu"));
        assertEquals(new Name("Long Tetulu"), descriptor.getName().get());
    }

    @Test
    void getSetSex() {
        Body body = new BodyBuilder(ALICE).build();
        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor(body);
        descriptor.setSex(Sex.MALE);
        assertEquals(Sex.MALE, descriptor.getSex().get());
    }

    @Test
    void getSetNric() {
        Body body = new BodyBuilder(ALICE).build();
        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor(body);
        descriptor.setNric(new Nric("F9123456B"));
        assertEquals(new Nric("F9123456B"), descriptor.getNric().get());
    }

    @Test
    void getSetReligion() {
        Body body = new BodyBuilder(ALICE).build();
        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor(body);
        descriptor.setReligion(Religion.ISLAM);
        assertEquals(Religion.ISLAM, descriptor.getReligion().get());
    }

    @Test
    void getSetCauseOfDeath() {
        Body body = new BodyBuilder(ALICE).build();
        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor(body);
        descriptor.setCauseOfDeath("Sepsis");
        assertEquals("Sepsis", descriptor.getCauseOfDeath().get());
    }

    @Test
    void getSetOrgansForDonation() {
        Body body = new BodyBuilder(ALICE).build();
        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor(body);

        ArrayList<String> list = new ArrayList<>(3);
        list.add("kidney");
        list.add("cornea");
        descriptor.setOrgansForDonation(list);
        assertEquals(list, descriptor.getOrgansForDonation().get());
    }

    @Test
    void getSetStatus() {
        Body body = new BodyBuilder(ALICE).build();
        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor(body);
        descriptor.setBodyStatus(BodyStatus.PENDING_IDENTIFICATION);
        assertEquals(BodyStatus.PENDING_IDENTIFICATION, descriptor.getBodyStatus().get());
    }

    @Test
    void getSetFridgeId() {
        Body body = new BodyBuilder(ALICE).build();
        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor(body);
        descriptor.setFridgeId(IdentificationNumber.customGenerateId("F", 5));
        assertEquals(IdentificationNumber.customGenerateId("F", 5), descriptor.getFridgeId().get());
    }

    @Test
    void getSetDateOfBirth() throws ParseException {
        Body body = new BodyBuilder(ALICE).build();
        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor(body);
        Date birthday = ParserUtil.parseDate("01/02/2018");
        descriptor.setDateOfBirth(birthday);
        assertEquals(birthday, descriptor.getDateOfBirth().get());
    }

    @Test
    void getSetDateOfDeath() throws ParseException {
        Body body = new BodyBuilder(ALICE).build();
        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor(body);
        Date deathDay = ParserUtil.parseDate("01/05/2018");
        descriptor.setDateOfDeath(deathDay);
        assertEquals(deathDay, descriptor.getDateOfDeath().get());
    }

    @Test
    void getSetNextOfKin() {
        Body body = new BodyBuilder(ALICE).build();
        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor(body);
        descriptor.setNextOfKin(new Name("Sepe Lok Aew"));
        assertEquals(new Name("Sepe Lok Aew"), descriptor.getNextOfKin().get());
    }

    @Test
    void getSetRelationship() {
        Body body = new BodyBuilder(ALICE).build();
        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor(body);
        descriptor.setRelationship("Mother");
        assertEquals("Mother", descriptor.getRelationship().get());
    }


    @Test
    void getSetKinPhoneNumber() {
        Body body = new BodyBuilder(ALICE).build();
        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor(body);
        descriptor.setKinPhoneNumber(new PhoneNumber("98981234"));
        assertEquals(new PhoneNumber("98981234"), descriptor.getKinPhoneNumber().get());
    }

    @Test
    void equals() {
        Body body = new BodyBuilder().build();
        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor(body);
        UpdateBodyDescriptor copyDescriptor = new UpdateBodyDescriptor(body);
        assertEquals(descriptor, copyDescriptor);
        assertEquals(descriptor.hashCode(), copyDescriptor.hashCode());
    }
}
