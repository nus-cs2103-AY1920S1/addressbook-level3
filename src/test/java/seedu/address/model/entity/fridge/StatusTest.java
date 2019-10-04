package seedu.address.model.entity.fridge;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

//@author arjavibahety
class StatusTest {

    @Test
    void enumerateStatus_indexOne_occupied() {
        assertEquals(Status.OCCUPIED.toString(), "OCCUPIED");
    }

    @Test
    void enumerateStatus_indexOne_unoccupied() {
        assertEquals(Status.UNOCCUPIED.toString(), "UNOCCUPIED");
    }
}
