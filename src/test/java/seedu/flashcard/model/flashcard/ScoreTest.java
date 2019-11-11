package seedu.flashcard.model.flashcard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.flashcard.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ScoreTest {


    @Test
    public void constructor_invalidScore_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Score(-1, -1));
        assertThrows(IllegalArgumentException.class, () -> new Score(0, -1));
        assertThrows(IllegalArgumentException.class, () -> new Score(-1, 0));
    }

    @Test
    public void isValidScoreString() {
        // null Score
        assertThrows(NullPointerException.class, () -> Score.isValidScore(null));

        // invalid Score
        assertFalse(Score.isValidScore("")); // empty string
        assertFalse(Score.isValidScore(" ")); // spaces only
        assertFalse(Score.isValidScore("-1 0")); // wrong correctAns
        assertFalse(Score.isValidScore("0 -1 ")); // wrong wrongAns
        assertFalse(Score.isValidScore("-1 -1")); // both wrong


        // valid Score
        assertTrue(Score.isValidScore("0 0"));
        assertTrue(Score.isValidScore("0 1"));
    }

    @Test
    public void isValidScoreInt() {

        // invalid Score
        assertFalse(Score.isValidScore(-1, 0)); // wrong correctAns
        assertFalse(Score.isValidScore(0, -1)); // wrong wrongAns
        assertFalse(Score.isValidScore(-1, -1)); // both wrong


        // valid Score
        assertTrue(Score.isValidScore(0, 0));
        assertTrue(Score.isValidScore(0, 1));
    }
}


