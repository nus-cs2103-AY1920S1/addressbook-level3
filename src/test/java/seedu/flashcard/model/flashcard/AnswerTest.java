package seedu.flashcard.model.flashcard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.flashcard.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AnswerTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Answer(null));
    }

    @Test
    public void constructor_invalidAnswer_throwsIllegalArgumentException() {
        String invalidAnswer = "";
        assertThrows(IllegalArgumentException.class, () -> new Answer(invalidAnswer));
    }

    @Test
    public void isValidAnswer() {

        // null Answer
        assertThrows(NullPointerException.class, () -> Answer.isValidAnswer(null));

        // invalid Answer
        assertFalse(Answer.isValidAnswer("")); // empty string
        assertFalse(Answer.isValidAnswer(" ")); // spaces only

        // valid Answer
        assertTrue(Answer.isValidAnswer("3"));
        assertTrue(Answer.isValidAnswer("/")); // one character
        assertTrue(Answer.isValidAnswer("Builds applications by combining "
            + "functionalities packaged as programmatically")); // long answer

    }
}
