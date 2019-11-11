package seedu.address.model.flashcard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

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
        // null answer
        assertThrows(NullPointerException.class, () -> Answer.isValidAnswer(null));

        // invalid answer
        assertFalse(Answer.isValidAnswer("")); // empty string
        assertFalse(Answer.isValidAnswer(" ")); // spaces only

        // valid answer
        assertTrue(Answer.isValidAnswer("2")); // an integer
        assertTrue(Answer.isValidAnswer("hello")); // a word
        assertTrue(Answer.isValidAnswer("Today's weather is good")); // a sentence
    }
}
