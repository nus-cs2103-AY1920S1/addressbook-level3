package seedu.address.model.body;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

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
