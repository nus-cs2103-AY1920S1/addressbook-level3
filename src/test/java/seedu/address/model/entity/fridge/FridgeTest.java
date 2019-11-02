package seedu.address.model.entity.fridge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import seedu.address.model.entity.UniqueIdentificationNumberMaps;
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
        // dummy required since ALICE_FRIDGE will be processed first and `aliceFridge` will not have ID = F01
        Fridge dummy = new FridgeBuilder(ALICE_FRIDGE).build();
        UniqueIdentificationNumberMaps.clearAllEntries();

        Fridge aliceFridge = new FridgeBuilder(ALICE_FRIDGE).build();
        UniqueIdentificationNumberMaps.clearAllEntries();
        Fridge aliceFridgeCopy = new FridgeBuilder(ALICE_FRIDGE).build();
        UniqueIdentificationNumberMaps.clearAllEntries();
        Fridge emptyFridge = new FridgeBuilder(EMPTY_FRIDGE).build();
        UniqueIdentificationNumberMaps.clearAllEntries();
        Fridge emptyFridgeCopy = new FridgeBuilder(EMPTY_FRIDGE).build();

        aliceFridge.setBody(ALICE);
        EMPTY_FRIDGE.setBody(null);

        assertTrue(aliceFridge.equals(aliceFridge));

        assertEquals(aliceFridge.hashCode(), aliceFridgeCopy.hashCode());
        assertTrue(aliceFridge.equals(aliceFridgeCopy));

        assertTrue(emptyFridge.equals(emptyFridge));
        assertEquals(emptyFridge.hashCode(), emptyFridgeCopy.hashCode());
        assertTrue(emptyFridge.equals(emptyFridgeCopy));

        assertFalse(ALICE_FRIDGE.equals(null));
        assertFalse(EMPTY_FRIDGE.equals(null));

        // Different fields
        assertFalse(ALICE_FRIDGE.equals(BOB_FRIDGE));
        assertFalse(ALICE_FRIDGE.equals(EMPTY_FRIDGE));

        Fridge editedAliceFridge = new FridgeBuilder(ALICE_FRIDGE).withBody(BOB).build();
        assertFalse(ALICE_FRIDGE.equals(editedAliceFridge));


    }

    //@@author ambervoong
    @Test
    void getSetBodyIdNum() {
        Fridge fridge = ALICE_FRIDGE;
        fridge.setBodyId(3);
        assertEquals(3, fridge.getBodyId());
    }

    @Test
    void generateNewStoredFridge_wasStored() {
        Fridge storedFridge = Fridge.generateNewStoredFridge(10);
        assertEquals(IdentificationNumber.customGenerateId("F", 10), storedFridge.getIdNum());

    }

    @Test
    void generateNewStoredFridge_invalidIdNumber() {
        assertThrows(IllegalArgumentException.class, () -> Fridge.generateNewStoredFridge(0));
    }
    //@@author

    @Test
    void getFridgeIdNum() {
        UniqueIdentificationNumberMaps.clearAllEntries();
        Fridge emptyFridge = new FridgeBuilder(EMPTY_FRIDGE).build();
        assertEquals(IdentificationNumber.customGenerateId("F", 1),
                emptyFridge.getIdNum());
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
        UniqueIdentificationNumberMaps.clearAllEntries();
        Fridge occupiedFridge = new Fridge();
        occupiedFridge.setBody(JOHN);
        assertEquals(" Fridge ID: F01 Status: OCCUPIED Body: Optional[" + JOHN + "]", occupiedFridge.toString());
    }

    @Test
    void toString_emptyFridge() {
        UniqueIdentificationNumberMaps.clearAllEntries();
        Fridge emptyFridge = new FridgeBuilder(EMPTY_FRIDGE).build();
        assertEquals(" Fridge ID: F01 Status: UNOCCUPIED Body: Optional.empty", emptyFridge.toString());
    }
}
//@@author
