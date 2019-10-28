package seedu.address.model.question;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class AnswerTest {

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
        // null address
        assertThrows(NullPointerException.class, () -> Answer.isValidAnswer(null));

        // invalid addresses
        assertFalse(Answer.isValidAnswer("")); // empty string
        assertFalse(Answer.isValidAnswer(" ")); // spaces only

        // valid addresses
        assertTrue(Answer.isValidAnswer("UML diagram"));
        assertTrue(Answer.isValidAnswer("2")); // one character
        assertTrue(Answer.isValidAnswer("open for extension but closed for modification")); // long address
    }

}
