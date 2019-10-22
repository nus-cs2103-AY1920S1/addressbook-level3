package seedu.address.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalBodies.JOHN;
import static seedu.address.testutil.TypicalFridges.EMPTY_FRIDGE;
import static seedu.address.testutil.TypicalWorkers.ZACH;

import org.junit.jupiter.api.Test;

class UniqueIdentificationNumberMapsTest {

    private static UniqueIdentificationNumberMaps uniqueIds = new UniqueIdentificationNumberMaps();

    @Test
    void addBody_returnsIdOne() {
        uniqueIds.clearAllEntries();
        assertEquals(1, uniqueIds.addEntity(JOHN));
        assertEquals(1, uniqueIds.addEntity(ZACH));
        assertEquals(1, uniqueIds.addEntity(EMPTY_FRIDGE));
    }

    @Test
    void containsWorkerId_true() {
        uniqueIds.addEntity(ZACH);
        uniqueIds.containsWorkerId(1);
    }

    @Test
    void containsBodyId_true() {
        uniqueIds.addEntity(JOHN);
        uniqueIds.containsBodyId(1);
    }

    @Test
    void containsFridgeId_true() {
        uniqueIds.addEntity(EMPTY_FRIDGE);
        uniqueIds.containsFridgeId(1);
    }
}
