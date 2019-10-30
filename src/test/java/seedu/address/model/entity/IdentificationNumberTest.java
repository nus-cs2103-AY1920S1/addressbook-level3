package seedu.address.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalBodies.ALICE;
import static seedu.address.testutil.TypicalBodies.JANE;
import static seedu.address.testutil.TypicalBodies.JOHN;
import static seedu.address.testutil.TypicalFridges.EMPTY_FRIDGE;
import static seedu.address.testutil.TypicalWorkers.ZACH;

import org.junit.jupiter.api.Test;

import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.fridge.Fridge;
import seedu.address.model
    .entity.worker.Worker;
import seedu.address.testutil.BodyBuilder;
import seedu.address.testutil.FridgeBuilder;
import seedu.address.testutil.WorkerBuilder;

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
    void generateNewBodyId_customId_true() {
        uniqueIds.clearAllEntries();
        IdentificationNumber testId = IdentificationNumber.generateNewBodyId(ALICE, 5);
        assertEquals("B00000005", testId.toString());
    }

    @Test
    void generateNewWorkerId_true() {
        uniqueIds.clearAllEntries();
        IdentificationNumber testId = IdentificationNumber.generateNewWorkerId(ZACH);
        assertEquals("W00001", testId.toString());
    }

    @Test
    void generateNewWorkerId_customId_true() {
        uniqueIds.clearAllEntries();
        IdentificationNumber testId = IdentificationNumber.generateNewWorkerId(ZACH, 1);
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
    void isValidIdentificationNumber_validIds_true() {
        assertTrue(IdentificationNumber.isValidIdentificationNumber("F01"));
        assertTrue(IdentificationNumber.isValidIdentificationNumber("W00005"));
        assertTrue(IdentificationNumber.isValidIdentificationNumber("B00000001"));
    }

    @Test
    void isValidIdentificationNumber_invalidIds_false() {
        assertFalse(IdentificationNumber.isValidIdentificationNumber(""));
        assertFalse(IdentificationNumber.isValidIdentificationNumber(" "));
        assertFalse(IdentificationNumber.isValidIdentificationNumber("F1"));
        assertFalse(IdentificationNumber.isValidIdentificationNumber("W0005"));
        assertFalse(IdentificationNumber.isValidIdentificationNumber("B0000001"));
    }

    @Test
    void isExistingIdentificationNumber_existingIds_true() {
        UniqueIdentificationNumberMaps.addEntity(JOHN);
        UniqueIdentificationNumberMaps.addEntity(ZACH);
        UniqueIdentificationNumberMaps.addEntity(EMPTY_FRIDGE);
        assertTrue(IdentificationNumber.isExistingIdentificationNumber("B00000001"));
        assertTrue(IdentificationNumber.isExistingIdentificationNumber("W00001"));
        assertTrue(IdentificationNumber.isExistingIdentificationNumber("F01"));
    }

    //@@author ambervoong
    @Test
    void addMapping_correctEntityReturned() {
        UniqueIdentificationNumberMaps.clearAllEntries();
        Body body = new BodyBuilder().build();
        IdentificationNumber id = body.getIdNum();
        id.addMapping(body);
        assertEquals(body, body.getIdNum().getMapping());
    }
    //@@author

    @Test
    void getMapping_correctEntityReturned() {
        UniqueIdentificationNumberMaps.clearAllEntries();
        Worker worker = new WorkerBuilder().build();
        Body body = new BodyBuilder().build();
        Fridge fridge = new FridgeBuilder().build();
        UniqueIdentificationNumberMaps.addEntity(worker);
        UniqueIdentificationNumberMaps.addEntity(body);
        assertEquals(worker, worker.getIdNum().getMapping());
        assertEquals(body, body.getIdNum().getMapping());
        assertEquals(fridge, fridge.getIdNum().getMapping());
    }

}
