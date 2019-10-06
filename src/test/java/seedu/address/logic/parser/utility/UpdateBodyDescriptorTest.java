package seedu.address.logic.parser.utility;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalBodies.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.model.entity.body.Body;
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
//        Body body = new BodyBuilder(ALICE).build();
//        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor(new BodyBuilder().build());
//        assertTrue(descriptor.apply(body).equals(new BodyBuilder().build()));

    }

    @Test
    void apply_somefieldsNotPresent_success() {
    }

    @Test
    void getName() {
    }

    @Test
    void setName() {
    }

    @Test
    void getSex() {
    }

    @Test
    void setSex() {
    }

    @Test
    void getNric() {
    }

    @Test
    void setNric() {
    }

    @Test
    void getReligion() {
    }

    @Test
    void setReligion() {
    }

    @Test
    void getCauseOfDeath() {
    }

    @Test
    void setCauseOfDeath() {
    }

    @Test
    void getOrgansForDonation() {
    }

    @Test
    void setOrgansForDonation() {
    }

    @Test
    void getStatus() {
    }

    @Test
    void setStatus() {
    }

    @Test
    void getFridgeId() {
    }

    @Test
    void setFridgeId() {
    }

    @Test
    void getDateOfBirth() {
    }

    @Test
    void setDateOfBirth() {
    }

    @Test
    void getDateOfDeath() {
    }

    @Test
    void setDateOfDeath() {
    }

    @Test
    void getNextOfKin() {
    }

    @Test
    void setNextOfKin() {
    }

    @Test
    void getRelationship() {
    }

    @Test
    void setRelationship() {
    }

    @Test
    void getKinPhoneNumber() {
    }

    @Test
    void setKinPhoneNumber() {
    }

    @Test
    void equals() {
        Body body = new BodyBuilder().build();
        UpdateBodyDescriptor descriptor =  new UpdateBodyDescriptor(body);
        UpdateBodyDescriptor copyDescriptor = new UpdateBodyDescriptor(body);
        assertEquals(descriptor, copyDescriptor);
        assertEquals(descriptor.hashCode(), copyDescriptor.hashCode());
    }
}