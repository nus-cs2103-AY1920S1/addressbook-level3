package seedu.address.model.body;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

//@@author ambervoong
class ReligionTest {

    @Test
    void enumerateReligion_indexOne_correct() {
        assertEquals(Religion.ISLAM.toString(), "ISLAM");
    }

    @Test
    void enumerateReligion_indexOne_wrong() {
        assertEquals(Religion.ISLAM.toString(), "12345");
    }

}
