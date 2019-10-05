package seedu.address.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class IdentificationNumberTest {

    @Test
    public void constructorIdNum_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new IdentificationNumber(null));
    }

    @Test
    public void constructorIdNum_emptyString_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new IdentificationNumber(""));
    }

    @Test
    void generateNewBodyId_true() {
        IdentificationNumber.resetCountOfBodies();
        IdentificationNumber testId = IdentificationNumber.generateNewBodyId();
        assertEquals("B00000001", testId.toString());
    }

    @Test
    void generateNewWorkerId_true() {
        IdentificationNumber.resetCountOfWorkers();
        IdentificationNumber testId = IdentificationNumber.generateNewWorkerId();
        assertEquals("W00001", testId.toString());
    }

    @Test
    void generateNewFridgeId_true() {
        IdentificationNumber.resetCountOfFridges();
        IdentificationNumber testId = IdentificationNumber.generateNewFridgeId();
        assertEquals("F01", testId.toString());
    }

    @Test
    void testEquals_differentAndNull_notEqual() {
        IdentificationNumber.resetCountOfBodies();
        IdentificationNumber.resetCountOfWorkers();
        IdentificationNumber.resetCountOfFridges();
        IdentificationNumber testId = IdentificationNumber.generateNewBodyId();
        assertNotEquals(testId, IdentificationNumber.generateNewBodyId());
        assertNotEquals(testId, IdentificationNumber.generateNewFridgeId());
        assertNotEquals(testId, IdentificationNumber.generateNewWorkerId());
        assertNotEquals(testId, null);
    }

    @Test
    void isValidIdentificationNumber_true() {
        assertTrue(IdentificationNumber.isValidIdentificationNumber("F01"));
        assertTrue(IdentificationNumber.isValidIdentificationNumber("W00005"));
        assertTrue(IdentificationNumber.isValidIdentificationNumber("B00000001"));
    }

}
