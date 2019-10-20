package seedu.address.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalBodies.JOHN;
import static seedu.address.testutil.TypicalFridges.EMPTY_FRIDGE;
import static seedu.address.testutil.TypicalWorkers.ZACH;

import org.junit.jupiter.api.Test;

class UniqueIdentificationNumberMapsTest {

    private static UniqueIdentificationNumberMaps uniqueIds = new UniqueIdentificationNumberMaps();

    @Test
    void addEntity_returnsIdOne() {
        uniqueIds.clearAllEntries();
        assertEquals(1, uniqueIds.addEntity(JOHN));
        assertEquals(1, uniqueIds.addEntity(ZACH));
        assertEquals(1, uniqueIds.addEntity(EMPTY_FRIDGE));
    }

    @Test
    void removeWorker_false() {
        uniqueIds.addEntity(ZACH);
        int id = ZACH.getIdNum().getIdNum();
        uniqueIds.removeWorkerId(id);
        assertFalse(uniqueIds.containsWorkerId(id));
    }

    @Test
    void removeBody_false() {
        uniqueIds.addEntity(JOHN);
        int id = JOHN.getIdNum().getIdNum();
        uniqueIds.removeBodyId(id);
        assertFalse(uniqueIds.containsBodyId(id));
    }

    @Test
    void removeFridge_false() {
        uniqueIds.addEntity(EMPTY_FRIDGE);
        int id = EMPTY_FRIDGE.getIdNum().getIdNum();
        uniqueIds.removeFridgeId(id);
        assertFalse(uniqueIds.containsFridgeId(id));
    }

    @Test
    void containsWorkerId_true() {
        uniqueIds.addEntity(ZACH);
        assertTrue(uniqueIds.containsWorkerId(1));
    }

    @Test
    void containsBodyId_true() {
        uniqueIds.addEntity(JOHN);
        assertTrue(uniqueIds.containsBodyId(1));
    }

    @Test
    void containsFridgeId_true() {
        uniqueIds.addEntity(EMPTY_FRIDGE);
        assertTrue(uniqueIds.containsFridgeId(1));
    }

    @Test
    void getMapping_addBody_true() {
        uniqueIds.addEntity(JOHN);
        assertEquals(JOHN, UniqueIdentificationNumberMaps.getMapping("B", 1));
    }

    @Test
    void getMapping_addWorker_true() {
        uniqueIds.addEntity(ZACH);
        assertEquals(ZACH, UniqueIdentificationNumberMaps.getMapping("W", 1));
    }

    @Test
    void getMapping_addFridge_true() {
        uniqueIds.addEntity(EMPTY_FRIDGE);
        assertEquals(EMPTY_FRIDGE, UniqueIdentificationNumberMaps.getMapping("F", 1));
    }

}
