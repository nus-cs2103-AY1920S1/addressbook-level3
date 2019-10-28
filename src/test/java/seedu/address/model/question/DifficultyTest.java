package seedu.address.model.question;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

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
    void isValidDifficulty() {
        // null difficulty
        assertThrows(NullPointerException.class, () -> Difficulty.isValidDifficulty(null));

        // invalid difficulty
        assertFalse(Difficulty.isValidDifficulty("")); // empty string
        assertFalse(Difficulty.isValidDifficulty(" ")); // spaces only

        // valid phone numbers
        assertTrue(Difficulty.isValidDifficulty("easy"));
        assertTrue(Difficulty.isValidDifficulty("easy to medium"));
        assertTrue(Difficulty.isValidDifficulty("specially hard, needs extra revision")); // long difficulty
    }
}
