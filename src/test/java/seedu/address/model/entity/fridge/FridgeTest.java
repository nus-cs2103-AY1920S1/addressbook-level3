package seedu.address.model.entity.fridge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalBodies.ALICE;
import static seedu.address.testutil.TypicalBodies.BOB;
import static seedu.address.testutil.TypicalFridges.ALICE_FRIDGE;
import static seedu.address.testutil.TypicalFridges.BOB_FRIDGE;
import static seedu.address.testutil.TypicalFridges.EMPTY_FRIDGE;

import org.junit.jupiter.api.Test;

import seedu.address.model.entity.IdentificationNumber;

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

    @Test
    void getFridgeIdNum() {
        assertEquals(IdentificationNumber.customGenerateId("F", 2),
                ALICE_FRIDGE.getFridgeIdNum());
    }

    @Test
    void getStatus() {
        assertEquals(EMPTY_FRIDGE.getStatus(), Status.UNOCCUPIED);
        assertEquals(ALICE_FRIDGE.getStatus(), Status.OCCUPIED);
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
        ALICE_FRIDGE.setStatus(Status.UNOCCUPIED);
        assertTrue(ALICE_FRIDGE.getStatus() == Status.UNOCCUPIED);
        ALICE_FRIDGE.setStatus(Status.OCCUPIED);

        EMPTY_FRIDGE.setStatus(Status.OCCUPIED);
        assertTrue(EMPTY_FRIDGE.getStatus() == Status.OCCUPIED);
        EMPTY_FRIDGE.setStatus(Status.UNOCCUPIED);

    }

}
