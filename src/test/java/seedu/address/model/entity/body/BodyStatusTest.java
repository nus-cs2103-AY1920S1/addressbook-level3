package seedu.address.model.entity.body;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

//@@author ambervoong
class BodyStatusTest {

    @Test
    void enumerateStatus_indexOne_correct() {
        assertEquals(BodyStatus.CLAIMED.toString(), "CLAIMED");
    }

}

