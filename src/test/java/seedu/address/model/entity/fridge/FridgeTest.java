package seedu.address.model.entity.fridge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalBodies.ALICE;
import static seedu.address.testutil.TypicalBodies.BOB;
import static seedu.address.testutil.TypicalBodies.JOHN;
import static seedu.address.testutil.TypicalFridges.ALICE_FRIDGE;
import static seedu.address.testutil.TypicalFridges.BOB_FRIDGE;
import static seedu.address.testutil.TypicalFridges.EMPTY_FRIDGE;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.model.entity.IdentificationNumber;
import seedu.address.testutil.FridgeBuilder;

//@@author arjavibahety
public class FridgeTest {

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
    }

    @Test
    public void equals() {
        ALICE_FRIDGE.setBody(ALICE);
        BOB_FRIDGE.setBody(BOB);
        EMPTY_FRIDGE.setBody(null);

        Fridge emptyFridgeCopy = new FridgeBuilder(EMPTY_FRIDGE).build();
        Fridge aliceFridgeCopy = new FridgeBuilder(ALICE_FRIDGE).build();

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

        Fridge editedAliceFridge = new FridgeBuilder(ALICE_FRIDGE).withBody(BOB).build();
        assertFalse(ALICE_FRIDGE.equals(editedAliceFridge));


    }

    @Test
    void getFridgeIdNum() {
        assertEquals(IdentificationNumber.customGenerateId("F", 1),
                EMPTY_FRIDGE.getIdNum());
    }

    @Test
    void getFridgeStatus() {
        assertEquals(EMPTY_FRIDGE.getFridgeStatus(), FridgeStatus.UNOCCUPIED);
        assertEquals(ALICE_FRIDGE.getFridgeStatus(), FridgeStatus.OCCUPIED);
    }

    @Test
    void getBody() {
        assertEquals(EMPTY_FRIDGE.getBody(), Optional.empty());
        assertEquals(ALICE_FRIDGE.getBody().get(), ALICE);
    }

    @Test
    void getSetBody() {
        ALICE_FRIDGE.setBody(BOB);
        assertTrue(ALICE_FRIDGE.getBody().equals(Optional.of(BOB)));
        ALICE_FRIDGE.setBody(ALICE);

        EMPTY_FRIDGE.setBody(BOB);
        assertTrue(EMPTY_FRIDGE.getBody().equals(Optional.of(BOB)));
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

    @Test
    void toString_occupiedFridge() {
        Fridge occupiedFridge = new Fridge(true);
        occupiedFridge.setBody(JOHN);
        assertEquals(" Fridge ID: F01 Status: OCCUPIED Body: Optional[" + JOHN + "]", occupiedFridge.toString());
    }

    @Test
    void toString_emptyFridge() {
        assertEquals(" Fridge ID: F01 Status: UNOCCUPIED Body: Optional.empty", EMPTY_FRIDGE.toString());
    }
}
