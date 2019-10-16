package seedu.address.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalBodies.JANE;
import static seedu.address.testutil.TypicalBodies.JOHN;
import static seedu.address.testutil.TypicalFridges.EMPTY_FRIDGE;
import static seedu.address.testutil.TypicalWorkers.ZACH;

import org.junit.jupiter.api.Test;

class IdentificationNumberTest {

    private static UniqueIdentificationNumberMaps uniqueIds = new UniqueIdentificationNumberMaps();

    @Test
    public void constructorIdNum_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new IdentificationNumber(null));
    }

    @Test
    void generateNewBodyId_true() {
        uniqueIds.clearAllEntries();
        IdentificationNumber testId = IdentificationNumber.generateNewBodyId(JOHN);
        assertEquals("B00000001", testId.toString());
    }

    @Test
    void generateNewWorkerId_true() {
        uniqueIds.clearAllEntries();
        IdentificationNumber testId = IdentificationNumber.generateNewWorkerId(ZACH);
        assertEquals("W00001", testId.toString());
    }

    @Test
    void generateNewFridgeId_true() {
        uniqueIds.clearAllEntries();
        IdentificationNumber testId = IdentificationNumber.generateNewFridgeId(EMPTY_FRIDGE);
        assertEquals("F01", testId.toString());
    }

    @Test
    void testEquals_differentAndNull_notEqual() {
        IdentificationNumber testId = IdentificationNumber.generateNewBodyId(JOHN);
        assertNotEquals(testId, IdentificationNumber.generateNewBodyId(JANE));
        assertNotEquals(testId, IdentificationNumber.generateNewFridgeId(EMPTY_FRIDGE));
        assertNotEquals(testId, IdentificationNumber.generateNewWorkerId(ZACH));
        assertNotEquals(testId, null);
    }

    @Test
    void isValidIdentificationNumber_true() {
        assertTrue(IdentificationNumber.isValidIdentificationNumber("F01"));
        assertTrue(IdentificationNumber.isValidIdentificationNumber("W00005"));
        assertTrue(IdentificationNumber.isValidIdentificationNumber("B00000001"));
    }

    @Test
    void isValidIdentificationNumber_false() {
        assertFalse(IdentificationNumber.isValidIdentificationNumber(""));
        assertFalse(IdentificationNumber.isValidIdentificationNumber(" "));
        assertFalse(IdentificationNumber.isValidIdentificationNumber("F1"));
        assertFalse(IdentificationNumber.isValidIdentificationNumber("W0005"));
        assertFalse(IdentificationNumber.isValidIdentificationNumber("B0000001"));
    }

}
