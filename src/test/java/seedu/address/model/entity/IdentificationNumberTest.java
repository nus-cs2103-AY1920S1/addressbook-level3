package seedu.address.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalBodies.JANE;
import static seedu.address.testutil.TypicalBodies.JOHN;
import static seedu.address.testutil.TypicalFridges.EMPTY_FRIDGE;
import static seedu.address.testutil.TypicalWorkers.ZACH;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.fridge.Fridge;
import seedu.address.model
    .entity.worker.Worker;
import seedu.address.testutil.BodyBuilder;
import seedu.address.testutil.FridgeBuilder;
import seedu.address.testutil.WorkerBuilder;

//@@author shaoyi1997
class IdentificationNumberTest {

    @BeforeEach
    void setUp() {
        // preloads all typical entities so that all IDs of all typical entities will be generated first
        Worker worker = ZACH;
        Body body = JOHN;
        Fridge fridge = EMPTY_FRIDGE;

        // clears all mapping to simulate a clean state of UniqueIdentificationNumberMaps
        UniqueIdentificationNumberMaps.clearAllEntries();

        /*
        the order of clearing after loading all entities is important
        preloading all typical entities prevents interpreter from reloading *all* typical entities again in each test
        otherwise, any new entities created, including the use of any typical entities, will not start from ID 1
         */
    }

    @Test
    public void constructorIdNum_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new IdentificationNumber(null));
    }

    @Test
    void generateNewBodyId_true() {
        IdentificationNumber testId = IdentificationNumber.generateNewBodyId(JOHN);
        assertEquals("B00000001", testId.toString());
    }

    @Test
    void generateNewBodyId_customId_true() {
        IdentificationNumber testId = IdentificationNumber.generateNewBodyId(JOHN, 5);
        assertEquals("B00000005", testId.toString());
    }

    @Test
    void generateNewWorkerId_true() {
        IdentificationNumber testId = IdentificationNumber.generateNewWorkerId(ZACH);
        assertEquals("W00001", testId.toString());
    }

    @Test
    void generateNewWorkerId_customId_true() {
        IdentificationNumber testId = IdentificationNumber.generateNewWorkerId(ZACH, 1);
        assertEquals("W00001", testId.toString());
    }

    @Test
    void generateNewFridgeId_true() {
        IdentificationNumber testId = IdentificationNumber.generateNewFridgeId(EMPTY_FRIDGE);
        assertEquals("F01", testId.toString());
    }

    @Test
    void generateNewFridgeId_customId_true() {
        IdentificationNumber testId = IdentificationNumber.generateNewFridgeId(EMPTY_FRIDGE, 1);
        assertEquals("F01", testId.toString());
    }

    @Test
    void generateNewId_invalidIdNum_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> IdentificationNumber.generateNewBodyId(JOHN, 0));
        assertThrows(AssertionError.class, () -> IdentificationNumber.generateNewBodyId(JOHN, -1));
        assertThrows(AssertionError.class, () -> IdentificationNumber.generateNewWorkerId(ZACH, 0));
        assertThrows(AssertionError.class, () -> IdentificationNumber.generateNewWorkerId(ZACH, -1));
        assertThrows(AssertionError.class, () -> IdentificationNumber.generateNewFridgeId(EMPTY_FRIDGE, 0));
        assertThrows(AssertionError.class, () -> IdentificationNumber.generateNewFridgeId(EMPTY_FRIDGE, -1));
    }

    @Test
    void testEquals_differentIdsAndNull_notEqual() {
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
        // empty strings
        assertFalse(IdentificationNumber.isValidIdentificationNumber(""));
        assertFalse(IdentificationNumber.isValidIdentificationNumber(" "));

        // incorrect number of zero paddings
        assertFalse(IdentificationNumber.isValidIdentificationNumber("F1"));
        assertFalse(IdentificationNumber.isValidIdentificationNumber("W0005"));
        assertFalse(IdentificationNumber.isValidIdentificationNumber("B0000001"));

        // incorrect prefix
        assertFalse(IdentificationNumber.isValidIdentificationNumber("G01"));
        assertFalse(IdentificationNumber.isValidIdentificationNumber("Q00001"));
        assertFalse(IdentificationNumber.isValidIdentificationNumber("V00000001"));
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

    @Test
    void isExistingIdentificationNumber_nonExistingIds_false() {
        assertFalse(IdentificationNumber.isExistingIdentificationNumber("B99999999"));
        assertFalse(IdentificationNumber.isExistingIdentificationNumber("W99999"));
        assertFalse(IdentificationNumber.isExistingIdentificationNumber("F99"));
    }

    //@@author ambervoong
    @Test
    void addMapping_correctEntityReturned() {
        Body body = new BodyBuilder().build();
        IdentificationNumber id = body.getIdNum();
        id.addMapping(body);
        assertEquals(body, body.getIdNum().getMapping());
    }
    //@@author

    @Test
    void getMapping_idsWithMappedEntities_correctEntityReturned() {
        Worker worker = new WorkerBuilder().build();
        Body body = new BodyBuilder().build();
        Fridge fridge = new FridgeBuilder().build();
        UniqueIdentificationNumberMaps.addEntity(worker);
        UniqueIdentificationNumberMaps.addEntity(body);
        assertEquals(worker, worker.getIdNum().getMapping());
        assertEquals(body, body.getIdNum().getMapping());
        assertEquals(fridge, fridge.getIdNum().getMapping());
    }

    @Test
    void getMapping_noMappedEntities_returnsNull() {
        IdentificationNumber id = IdentificationNumber.customGenerateId("F", 99);
        assertNull(id.getMapping());
    }

}
//@@author
