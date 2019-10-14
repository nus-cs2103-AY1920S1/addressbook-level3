package com.dukeacademy.model.question;

import static com.dukeacademy.testutil.Assert.assertThrows;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DifficultyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Difficulty(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        assertThrows(IllegalArgumentException.class, () -> new Difficulty(invalidAddress));
    }

    @Test
    public void isValidDifficulty() {
        // null difficulty
        assertThrows(NullPointerException.class, () -> Difficulty.isValidDifficulty(null));

        // invalid addresses
        assertFalse(Difficulty.isValidDifficulty("")); // empty string
        assertFalse(Difficulty.isValidDifficulty(" ")); // spaces only

        // valid addresses
        assertTrue(Difficulty.isValidDifficulty("Blk 456, Den Road, #01-355"));
        assertTrue(Difficulty.isValidDifficulty("-")); // one character
        assertTrue(Difficulty.isValidDifficulty("Leng Inc; 1234 Market St; "
            + "San Francisco CA 2349879; USA")); // long difficulty
    }
}
