package seedu.algobase.model.problem;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.algobase.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class DifficultyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Difficulty(null));
    }

    @Test
    public void constructor_invalidDifficulty_throwsIllegalArgumentException() {
        String invalidDifficulty = "";
        assertThrows(IllegalArgumentException.class, () -> new Difficulty(invalidDifficulty));
    }

    @Test
    public void isValidDifficulty() {
        // null difficulty
        assertThrows(NullPointerException.class, () -> Difficulty.isValidDifficulty(null));

        // invalid difficulty
        assertFalse(Difficulty.isValidDifficulty("")); // empty string
        assertFalse(Difficulty.isValidDifficulty(" ")); // only spaces
        assertFalse(Difficulty.isValidDifficulty("abcd")); // non-numeric characters
        assertFalse(Difficulty.isValidDifficulty("1234abc1234")); // contains non-numeric characters
        assertFalse(Difficulty.isValidDifficulty("-0.1")); // exceed lower bound
        assertFalse(Difficulty.isValidDifficulty("5.0001")); // exceed upper bound
        assertFalse(Difficulty.isValidDifficulty("0")); // boundary value

        // valid difficulty
        assertTrue(Difficulty.isValidDifficulty("0.0000000001")); // boundary value
        assertTrue(Difficulty.isValidDifficulty("1.2")); // common number
        assertTrue(Difficulty.isValidDifficulty("4.9999")); // boundary value
        assertTrue(Difficulty.isValidDifficulty("5")); // boundary value
        assertTrue(Difficulty.isValidDifficulty("4.123456789123456789")); // long string
    }

}
