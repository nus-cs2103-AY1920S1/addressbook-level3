package seedu.address.model.entity.body;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.entity.fridge.FridgeStatus;

//@@author ambervoong
class StatusTest {

    @Test
    void enumerateStatus_indexOne_correct() {
        assertEquals(BodyStatus.CLAIMED.toString(), "CLAIMED");
    }

}