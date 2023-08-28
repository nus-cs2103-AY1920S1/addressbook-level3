package seedu.address.logic.parser.utility;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalBodies.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.model.entity.UniqueIdentificationNumberMaps;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.fridge.Fridge;
import seedu.address.model.entity.fridge.FridgeStatus;
import seedu.address.testutil.BodyBuilder;
import seedu.address.testutil.FridgeBuilder;

//@@author ambervoong-unused
class UpdateFridgeDescriptorTest {

    @Test
    void isAnyFieldEdited_fieldEdited_true() {
        UpdateFridgeDescriptor descriptor = new UpdateFridgeDescriptor();
        descriptor.setFridgeStatus(FridgeStatus.OCCUPIED);
        assertTrue(descriptor.isAnyFieldEdited());
    }

    @Test
    void isAnyFieldEdited_fieldNotEdited_false() {
        UpdateFridgeDescriptor descriptor = new UpdateFridgeDescriptor();
        assertFalse(descriptor.isAnyFieldEdited());
    }

    @Test
    void apply_fieldsPresent_success() {
        Body dummyAlice = new BodyBuilder(ALICE).build();
        UniqueIdentificationNumberMaps.clearAllEntries();
        Fridge fridge = new FridgeBuilder().build();
        UniqueIdentificationNumberMaps.clearAllEntries();
        Body body = new BodyBuilder(ALICE).build();
        UpdateFridgeDescriptor descriptor = new UpdateFridgeDescriptor();
        descriptor.setBodyId(body.getIdNum());
        descriptor.setNewBody(body);
        descriptor.setFridgeStatus(FridgeStatus.OCCUPIED);

        UniqueIdentificationNumberMaps.clearAllEntries();
        Fridge fridgeCopy = new FridgeBuilder().build();
        fridgeCopy.setBody(body);
        fridgeCopy.setFridgeStatus(FridgeStatus.OCCUPIED);

        assertTrue(descriptor.apply(fridge).equals(fridgeCopy));
    }

    @Test
    void apply_allFieldsNotPresent_success() {
        //  Success because apply does not check whether fields are present or not.
        Body dummyAlice = new BodyBuilder(ALICE).build();
        UniqueIdentificationNumberMaps.clearAllEntries();

        Fridge fridge = new FridgeBuilder().build();
        Body body = new BodyBuilder(ALICE).build();
        UpdateFridgeDescriptor descriptor = new UpdateFridgeDescriptor();
        descriptor.setNewBody(body);
        descriptor.setBodyId(body.getIdNum());

        UniqueIdentificationNumberMaps.clearAllEntries();
        Fridge fridgeCopy = new FridgeBuilder().build();
        fridgeCopy.setBody(body);

        assertTrue(descriptor.apply(fridge).equals(fridgeCopy));
    }

    @Test
    void getSetBody() {
        Body body = new BodyBuilder(ALICE).build();
        UpdateFridgeDescriptor descriptor = new UpdateFridgeDescriptor();
        descriptor.setBodyId(body.getIdNum());
        assertEquals(descriptor.getBodyId().get(), body.getIdNum());
    }

    @Test
    void getSetFridgeStatus() {
        UpdateFridgeDescriptor descriptor = new UpdateFridgeDescriptor();
        descriptor.setFridgeStatus(FridgeStatus.OCCUPIED);
        assertEquals(descriptor.getFridgeStatus().get(), FridgeStatus.OCCUPIED);
    }

    @Test
    void getSetNewBody() {
        Body body = new BodyBuilder().build();
        UpdateFridgeDescriptor descriptor = new UpdateFridgeDescriptor();
        descriptor.setNewBody(body);
        assertEquals(descriptor.getNewBody().get(), body);
    }

    @Test
    void equals() {
        Fridge fridge = new FridgeBuilder().build();
        UpdateFridgeDescriptor descriptor = new UpdateFridgeDescriptor(fridge);
        UpdateFridgeDescriptor copyDescriptor = new UpdateFridgeDescriptor(fridge);
        assertEquals(descriptor, copyDescriptor);
        assertEquals(descriptor.hashCode(), copyDescriptor.hashCode());
    }
}
//@@author

