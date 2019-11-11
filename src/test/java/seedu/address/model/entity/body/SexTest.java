package seedu.address.model.entity.body;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void validSex_correct() {
        assertTrue(Sex.isMale("Male"));
        assertTrue(Sex.isFemale("female"));
        assertTrue(Sex.isValidSex("male"));
        assertTrue(Sex.isValidSex("Female"));
    }
}
