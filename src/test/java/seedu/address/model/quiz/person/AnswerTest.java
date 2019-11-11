package seedu.address.model.quiz.person;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AnswerTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Answer(null));
    }

    @Test
    public void isValidAnswer() {
        // null answer number
        assertThrows(NullPointerException.class, () -> Answer.isValidAnswer(null));

        // valid answer numbers
        assertTrue(Answer.isValidAnswer("")); // empty string
        assertTrue(Answer.isValidAnswer(" ")); // spaces only
        assertTrue(Answer.isValidAnswer("My Answer")); // alphabets within digits
        assertTrue(Answer.isValidAnswer("123 456")); // spaces within digits
        assertTrue(Answer.isValidAnswer("93121534"));
        assertTrue(Answer.isValidAnswer("124293842033123")); // long answer numbers
        assertTrue(Answer.isValidAnswer("911")); // exactly 3 numbers (too short)
    }
}
