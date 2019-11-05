package seedu.revision.model.answerable;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.revision.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class QuestionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Question(null));
    }

    @Test
    public void constructor_invalidQuestion_throwsIllegalArgumentException() {
        String invalidQuestion = "";
        assertThrows(IllegalArgumentException.class, () -> new Question(invalidQuestion));
    }

    @Test
    public void isValidQuestion() {
        //Boundary Value Analysis. EPs: [0],[1-300],[301, INT_MAX]. Not possible to have negative number of characters.
        // null question
        assertThrows(NullPointerException.class, () -> Question.isValidQuestion(null));

        //Boundary Value: Empty strings and whitespace characters
        assertFalse(Question.isValidQuestion("")); // empty string
        assertFalse(Question.isValidQuestion(" ")); // spaces only
        //Boundary Value: 300 characters
        assertTrue(Question.isValidQuestion("123456789 123456789 123456789 123456789 123456789 123456789 "
                + "123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 "
                + "123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 "
                + "123456789 123456789 123456789 123456789 "));
        //Boundary Value: 301 characters
        assertFalse(Question.isValidQuestion("123456789 123456789 123456789 123456789 123456789 123456789 "
                + "123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 "
                + "123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 "
                + "123456789 123456789 123456789 123456789 1"));

        // other valid questions
        assertTrue(Question.isValidQuestion("greenfield")); // alphabets
        assertTrue(Question.isValidQuestion("12345")); // numbers only
        assertTrue(Question.isValidQuestion("week 2")); // alphanumeric characters
        assertTrue(Question.isValidQuestion("What is the Capital of Singapore?")); // with capital letters
        assertTrue(Question.isValidQuestion("With a question mark?")); // with question mark

    }
}
