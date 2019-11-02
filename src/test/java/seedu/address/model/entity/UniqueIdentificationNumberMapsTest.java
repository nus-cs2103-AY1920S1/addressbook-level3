package seedu.address.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalBodies.ALICE;
import static seedu.address.testutil.TypicalBodies.JOHN;
import static seedu.address.testutil.TypicalFridges.ALICE_FRIDGE;
import static seedu.address.testutil.TypicalFridges.EMPTY_FRIDGE;
import static seedu.address.testutil.TypicalWorkers.BENSON;
import static seedu.address.testutil.TypicalWorkers.ZACH;

import org.junit.jupiter.api.Test;

import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.fridge.Fridge;
import seedu.address.model.entity.worker.Worker;
import seedu.address.testutil.BodyBuilder;
import seedu.address.testutil.FridgeBuilder;
import seedu.address.testutil.WorkerBuilder;

class UniqueIdentificationNumberMapsTest {

    private static UniqueIdentificationNumberMaps uniqueIds = new UniqueIdentificationNumberMaps();
    private static Body body = ALICE;
    private static Worker worker = BENSON;
    private static Fridge fridge = ALICE_FRIDGE;

    @Test
    void addEntity_returnsIdOne() {
        UniqueIdentificationNumberMaps.clearAllEntries();
        // id = 2 because build() for each entity creates id = 1 already
        assertEquals(2, uniqueIds.addEntity(new WorkerBuilder().build()));
        assertEquals(2, uniqueIds.addEntity(new BodyBuilder().build()));
        assertEquals(2, uniqueIds.addEntity(new FridgeBuilder().build()));
    }

    //@@author ambervoong
    @Test
    void addEntity_validCustomId_success() {
        UniqueIdentificationNumberMaps.clearAllEntries();
        uniqueIds.addEntity(body, 6);
        uniqueIds.addEntity(worker, 2);
        uniqueIds.addEntity(fridge, 6);
        assertEquals(uniqueIds.getMapping("B", 6), body);
        assertEquals(uniqueIds.getMapping("W", 2), worker);
        assertEquals(uniqueIds.getMapping("F", 6), fridge);
    }

    @Test
    void addEntity_nullEntity_failure() {
        assertThrows(AssertionError.class, () -> uniqueIds.addEntity(null));
    }
    //@@author

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
        UniqueIdentificationNumberMaps.clearAllEntries();
        Body body = new BodyBuilder().build();
        uniqueIds.addEntity(body);
        assertEquals(body, UniqueIdentificationNumberMaps.getMapping("B", 1));
    }

    @Test
    void getMapping_addWorker_true() {
        UniqueIdentificationNumberMaps.clearAllEntries();
        Worker worker = new WorkerBuilder().build();
        uniqueIds.addEntity(worker);
        assertEquals(worker, UniqueIdentificationNumberMaps.getMapping("W", 1));
    }

    @Test
    void getMapping_addFridge_true() {
        UniqueIdentificationNumberMaps.clearAllEntries();
        Fridge fridge = new FridgeBuilder().build();
        uniqueIds.addEntity(fridge);
        assertEquals(fridge, UniqueIdentificationNumberMaps.getMapping("F", 1));
    }

}
