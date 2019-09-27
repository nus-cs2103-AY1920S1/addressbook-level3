package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class QuestionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Question(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Question(invalidName));
    }

    @Test
    public void isValidQuestion() {
        // null name
        assertThrows(NullPointerException.class, () -> Question.isValidQuestion(null));

        // invalid name
        assertFalse(Question.isValidQuestion("")); // empty string
        assertFalse(Question.isValidQuestion(" ")); // spaces only

        // valid name
        assertTrue(Question.isValidQuestion("1 + 1 =")); // alphabets only
        assertTrue(Question.isValidQuestion("39 + 40 =")); // numbers only
        assertTrue(Question.isValidQuestion("What is time")); // alphanumeric characters
        assertTrue(Question.isValidQuestion("What is environmental model")); // with capital letters
        assertTrue(Question.isValidQuestion("Who is the fastest runner in the history")); // long names
    }
}
