package seedu.address.model.entity.body;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.entity.Sex;

//@@author ambervoong
class SexTest {

    @Test
    void enumerateSex_indexOne_correct() {
        assertEquals(Sex.FEMALE.toString(), "FEMALE");
    }

    @Test
    void enumerateSex_indexOne_wrong() {
        assertNotEquals(Sex.FEMALE.toString(), "MALE");
    }
}
