package seedu.address.model.entity.fridge;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

//@author arjavibahety
class FridgeStatusTest {

    @Test
    void enumerateStatus_indexOne_occupied() {
        assertEquals(FridgeStatus.OCCUPIED.toString(), "OCCUPIED");
    }

    @Test
    void enumerateStatus_indexOne_unoccupied() {
        assertEquals(FridgeStatus.UNOCCUPIED.toString(), "UNOCCUPIED");
    }
}
