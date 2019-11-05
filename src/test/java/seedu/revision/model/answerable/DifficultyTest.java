package seedu.revision.model.answerable;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.revision.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DifficultyTest {

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
        assertFalse(Difficulty.isValidDifficulty(" ")); // spaces only
        assertFalse(Difficulty.isValidDifficulty("91")); // less than 3 numbers
        assertFalse(Difficulty.isValidDifficulty("difficulty")); // non-numeric
        assertFalse(Difficulty.isValidDifficulty("9011p041")); // alphabets within digits
        assertFalse(Difficulty.isValidDifficulty("9312 1534")); // spaces within digits

        //Boundary Value Analysis. EPs: [INT_MIN, 0], [1-3], [4, INT_MAX]
        assertFalse(Difficulty.isValidDifficulty("0"));
        assertFalse(Difficulty.isValidDifficulty("4"));
        assertTrue(Difficulty.isValidDifficulty("1"));
        assertTrue(Difficulty.isValidDifficulty("3"));
    }
}
