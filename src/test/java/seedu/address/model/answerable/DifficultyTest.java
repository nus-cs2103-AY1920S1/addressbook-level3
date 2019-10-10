package seedu.address.model.answerable;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

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

        //TODO: Implement valid difficulty. These are not valid for test bank
        assertTrue(Difficulty.isValidDifficulty("911")); // exactly 3 numbers
        assertTrue(Difficulty.isValidDifficulty("93121534"));
        assertTrue(Difficulty.isValidDifficulty("124293842033123")); // long difficulty numbers
    }
}
