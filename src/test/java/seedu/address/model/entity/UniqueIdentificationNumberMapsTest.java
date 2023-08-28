package seedu.address.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalBodies.ALICE;
import static seedu.address.testutil.TypicalBodies.JOHN;
import static seedu.address.testutil.TypicalFridges.ALICE_FRIDGE;
import static seedu.address.testutil.TypicalFridges.EMPTY_FRIDGE;
import static seedu.address.testutil.TypicalWorkers.BENSON;
import static seedu.address.testutil.TypicalWorkers.ZACH;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.fridge.Fridge;
import seedu.address.model.entity.worker.Worker;
import seedu.address.testutil.BodyBuilder;
import seedu.address.testutil.FridgeBuilder;
import seedu.address.testutil.WorkerBuilder;

//@@author shaoyi1997
class UniqueIdentificationNumberMapsTest {

    private static UniqueIdentificationNumberMaps uniqueIds = new UniqueIdentificationNumberMaps();
    private static Body body = ALICE;
    private static Worker worker = BENSON;
    private static Fridge fridge = ALICE_FRIDGE;

    @BeforeEach
    void clearAllEntries() {
        UniqueIdentificationNumberMaps.clearAllEntries();
    }

    @Test
    void addEntity_validEntity_returnsIdTwo() {
        // id = 2 because build() for each entity creates id = 1 already
        assertEquals(2, uniqueIds.addEntity(new WorkerBuilder().build()));
        assertEquals(2, uniqueIds.addEntity(new BodyBuilder().build()));
        assertEquals(2, uniqueIds.addEntity(new FridgeBuilder().build()));
    }

    //@@author ambervoong
    @Test
    void addEntity_validCustomId_success() {
        uniqueIds.addEntity(body, 6);
        uniqueIds.addEntity(worker, 2);
        uniqueIds.addEntity(fridge, 6);
        assertEquals(uniqueIds.getMapping("B", 6), body);
        assertEquals(uniqueIds.getMapping("W", 2), worker);
        assertEquals(uniqueIds.getMapping("F", 6), fridge);
    }

    @Test
    void addEntity_nullEntity_failure() {
        assertThrows(NullPointerException.class, () -> uniqueIds.addEntity(null));
    }
    //@@author

    /**
     * Integration test for assigning a gap in the ID numbers to a newly added entity.
     */
    @Test
    void addEntity_addAfterRemoving_addedEntityTakesIdOfRemovedEntity() {
        Worker worker = new WorkerBuilder().build();
        uniqueIds.addEntity(worker); // id = 1
        uniqueIds.addEntity(ZACH); // id = 2
        assertEquals(worker, uniqueIds.removeWorkerId(1));
        assertEquals(1, new WorkerBuilder().build().getIdNum().getIdNum());
    }

    @Test
    void removeWorker_success() {
        Worker worker = new WorkerBuilder().build();
        int id = uniqueIds.addEntity(worker);
        assertEquals(worker, uniqueIds.removeWorkerId(id));
    }

    @Test
    void removeBody_success() {
        Body body = new BodyBuilder().build();
        int id = uniqueIds.addEntity(body);
        assertEquals(body, uniqueIds.removeBodyId(id));
    }

    @Test
    void removeFridge_success() {
        Fridge fridge = new FridgeBuilder().build();
        int id = uniqueIds.addEntity(fridge);
        assertEquals(fridge, uniqueIds.removeFridgeId(id));
    }

    @Test
    void removeWorker_invalidId_returnsNull() {
        assertNull(uniqueIds.removeWorkerId(Integer.MAX_VALUE));
    }

    @Test
    void containsWorkerId_existingId_true() {
        uniqueIds.addEntity(ZACH);
        assertTrue(uniqueIds.containsWorkerId(1));
    }

    @Test
    void containsWorkerId_nonExistingId_false() {
        uniqueIds.addEntity(ZACH);
        assertFalse(uniqueIds.containsWorkerId(Integer.MAX_VALUE));
    }

    @Test
    void containsWorkerId_idIsZero_false() {
        uniqueIds.addEntity(ZACH);
        assertFalse(uniqueIds.containsWorkerId(0));
    }

    @Test
    void containsWorkerId_idIsNegative_false() {
        uniqueIds.addEntity(ZACH);
        assertFalse(uniqueIds.containsWorkerId(-1));
    }

    @Test
    void containsBodyId_true() {
        uniqueIds.addEntity(JOHN);
        assertTrue(uniqueIds.containsBodyId(1));
    }

    @Test
    void containsBodyId_nonExistingId_false() {
        uniqueIds.addEntity(JOHN);
        assertFalse(uniqueIds.containsBodyId(Integer.MAX_VALUE));
    }

    @Test
    void containsFridgeId_true() {
        uniqueIds.addEntity(EMPTY_FRIDGE);
        assertTrue(uniqueIds.containsFridgeId(1));
    }

    @Test
    void containsFridgeId_nonExistingId_false() {
        uniqueIds.addEntity(EMPTY_FRIDGE);
        assertFalse(uniqueIds.containsFridgeId(Integer.MAX_VALUE));
    }

    @Test
    void getMapping_ofBodyId_success() {
        Body body = new BodyBuilder().build();
        uniqueIds.addEntity(body);
        assertEquals(body, UniqueIdentificationNumberMaps.getMapping("B", 1));
    }

    @Test
    void getMapping_ofWorkerId_success() {
        Worker worker = new WorkerBuilder().build();
        uniqueIds.addEntity(worker);
        assertEquals(worker, UniqueIdentificationNumberMaps.getMapping("W", 1));
    }

    @Test
    void getMapping_ofFridgeId_success() {
        Fridge fridge = new FridgeBuilder().build();
        uniqueIds.addEntity(fridge);
        assertEquals(fridge, UniqueIdentificationNumberMaps.getMapping("F", 1));
    }

    @Test
    void getMapping_ofBodyIdWithLowerCaseEntityType_success() {
        Body body = new BodyBuilder().build();
        uniqueIds.addEntity(body);
        assertEquals(body, UniqueIdentificationNumberMaps.getMapping("b", 1));
    }

    @Test
    void getMapping_invalidEntityType_returnsNull() {
        assertNull(UniqueIdentificationNumberMaps.getMapping("A", 1));
    }

    @Test
    void getMapping_invalidIdNumber_returnNull() {
        assertNull(UniqueIdentificationNumberMaps.getMapping("F", Integer.MAX_VALUE));
    }

}
//@@author
