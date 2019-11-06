package seedu.address.model.quiz.person;

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
        // null phone number
        assertThrows(NullPointerException.class, () -> Answer.isValidAnswer(null));

        // invalid phone numbers
        assertFalse(Answer.isValidAnswer("")); // empty string
        assertFalse(Answer.isValidAnswer(" ")); // spaces only

        // valid phone numbers
        assertTrue(Answer.isValidAnswer("My Answer")); // alphabets within digits
        assertTrue(Answer.isValidAnswer("123 456")); // spaces within digits
        assertTrue(Answer.isValidAnswer("911")); // exactly 3 numbers
        assertTrue(Answer.isValidAnswer("93121534"));
        assertTrue(Answer.isValidAnswer("124293842033123")); // long phone numbers
    }
}
