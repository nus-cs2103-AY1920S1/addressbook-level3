package seedu.address.model.entity.fridge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.FridgeBuilder.DEFAULT_BODY;
import static seedu.address.testutil.TypicalBodies.ALICE;
import static seedu.address.testutil.TypicalBodies.BOB;
import static seedu.address.testutil.TypicalFridges.ALICE_FRIDGE;
import static seedu.address.testutil.TypicalFridges.BOB_FRIDGE;
import static seedu.address.testutil.TypicalFridges.EMPTY_FRIDGE;

import org.junit.jupiter.api.Test;

import seedu.address.model.entity.IdentificationNumber;
import seedu.address.testutil.BodyBuilder;
import seedu.address.testutil.FridgeBuilder;

//@@author arjavibahety
public class FridgeTest {

    @Test
    public void isSameFridge() {
        // same object -> returns true
        assertTrue(ALICE_FRIDGE.isSameFridge(ALICE_FRIDGE));
        assertTrue(EMPTY_FRIDGE.isSameFridge(EMPTY_FRIDGE));

        // null -> returns false
        assertFalse(ALICE_FRIDGE.isSameFridge(null));
        assertFalse(EMPTY_FRIDGE.isSameFridge(null));

        // different fridges entirely -> returns false
        assertFalse(ALICE_FRIDGE.isSameFridge(BOB_FRIDGE));
        assertFalse(EMPTY_FRIDGE.isSameFridge(BOB_FRIDGE));
    }

    //@@author ambervoong
    @Test
    public void isSameEntity() {
        // same object -> returns true
        assertTrue(ALICE_FRIDGE.isSameEntity(ALICE_FRIDGE));
        assertTrue(EMPTY_FRIDGE.isSameEntity(EMPTY_FRIDGE));

        // null -> returns false
        assertFalse(ALICE_FRIDGE.isSameEntity(null));
        assertFalse(EMPTY_FRIDGE.isSameEntity(null));

        // different fridges entirely -> returns false
        assertFalse(ALICE_FRIDGE.isSameEntity(BOB_FRIDGE));
        assertFalse(EMPTY_FRIDGE.isSameEntity(BOB_FRIDGE));

        // different type
        assertFalse(ALICE_FRIDGE.isSameEntity(new BodyBuilder().build()));
    }
    //@@author

    @Test
    public void equals() {
        Fridge aliceFridgeCopy = new FridgeBuilder(ALICE_FRIDGE).build();
        Fridge emptyFridgeCopy = new FridgeBuilder(EMPTY_FRIDGE).build();

        assertTrue(ALICE_FRIDGE.equals(ALICE_FRIDGE));
        assertEquals(ALICE_FRIDGE.hashCode(), aliceFridgeCopy.hashCode());
        assertTrue(ALICE_FRIDGE.equals(aliceFridgeCopy));

        assertTrue(EMPTY_FRIDGE.equals(EMPTY_FRIDGE));
        assertEquals(EMPTY_FRIDGE.hashCode(), emptyFridgeCopy.hashCode());
        assertTrue(EMPTY_FRIDGE.equals(emptyFridgeCopy));

        assertFalse(ALICE_FRIDGE.equals(null));
        assertFalse(EMPTY_FRIDGE.equals(null));

        // Different fields
        assertFalse(ALICE_FRIDGE.equals(BOB_FRIDGE));
        assertFalse(ALICE_FRIDGE.equals(EMPTY_FRIDGE));

        Fridge editedAliceFridge = new FridgeBuilder(ALICE_FRIDGE).withBody(DEFAULT_BODY).build();
        assertFalse(ALICE_FRIDGE.equals(editedAliceFridge));

    }

    @Test
    void getFridgeIdNum() {
        assertEquals(IdentificationNumber.customGenerateId("F", 1),
                EMPTY_FRIDGE.getFridgeIdNum());
    }

    @Test
    void getFridgeStatus() {
        assertEquals(EMPTY_FRIDGE.getFridgeStatus(), FridgeStatus.UNOCCUPIED);
        assertEquals(ALICE_FRIDGE.getFridgeStatus(), FridgeStatus.OCCUPIED);
    }

    @Test
    void getBody() {
        assertEquals(EMPTY_FRIDGE.getBody(), null);
        assertEquals(ALICE_FRIDGE.getBody(), ALICE);
    }

    @Test
    void getSetBody() {
        ALICE_FRIDGE.setBody(BOB);
        assertTrue(ALICE_FRIDGE.getBody() == BOB);
        ALICE_FRIDGE.setBody(ALICE);

        EMPTY_FRIDGE.setBody(BOB);
        assertTrue(EMPTY_FRIDGE.getBody() == BOB);
        EMPTY_FRIDGE.setBody(null);
    }

    @Test
    void getSetStatus() {
        ALICE_FRIDGE.setFridgeStatus(FridgeStatus.UNOCCUPIED);
        assertTrue(ALICE_FRIDGE.getFridgeStatus() == FridgeStatus.UNOCCUPIED);
        ALICE_FRIDGE.setFridgeStatus(FridgeStatus.OCCUPIED);

        EMPTY_FRIDGE.setFridgeStatus(FridgeStatus.OCCUPIED);
        assertTrue(EMPTY_FRIDGE.getFridgeStatus() == FridgeStatus.OCCUPIED);
        EMPTY_FRIDGE.setFridgeStatus(FridgeStatus.UNOCCUPIED);

    }

}
