package seedu.revision.model.answerable;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.revision.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AnswerTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Question(null));
    }

    @Test
    public void constructor_invalidAnswer_throwsIllegalArgumentException() {
        String invalidAnswer = "";
        assertThrows(IllegalArgumentException.class, () -> new Answer(invalidAnswer));
    }

    @Test
    public void isValidAnswer() {
        //Boundary Value Analysis. EPs: [0],[1-150],[151, INT_MAX]. Not possible to have negative number of characters.
        // null question
        assertThrows(NullPointerException.class, () -> Answer.isValidAnswer(null));
        //Boundary Value: Empty strings and whitespace characters
        assertFalse(Answer.isValidAnswer("")); // empty string
        assertFalse(Answer.isValidAnswer(" ")); // spaces only
        //Boundary Value: 150 characters
        assertTrue(Answer.isValidAnswer("123456789 123456789 123456789 123456789 123456789 123456789 "
                + "123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 "
                + "123456789 ")); //150 characters
        //Boundary Value: 151 characters
        assertFalse(Answer.isValidAnswer("123456789 123456789 123456789 123456789 123456789 123456789 "
                + "123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 "
                + "123456789 1")); //151 characters

        // other valid questions
        assertTrue(Answer.isValidAnswer("greenfield")); // alphabets
        assertTrue(Answer.isValidAnswer("12345")); // numbers only
        assertTrue(Answer.isValidAnswer("week 2")); // alphanumeric characters
        assertTrue(Answer.isValidAnswer("What is the Capital of Singapore?")); // with capital letters
        assertTrue(Answer.isValidAnswer("With a question mark?")); // with question mark

    }
}
