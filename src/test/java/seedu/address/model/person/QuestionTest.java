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
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Question.isValidQuestion(null));

        // invalid name
        assertFalse(Question.isValidQuestion("")); // empty string
        assertFalse(Question.isValidQuestion(" ")); // spaces only
        assertFalse(Question.isValidQuestion("^")); // only non-alphanumeric characters
        assertFalse(Question.isValidQuestion("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Question.isValidQuestion("peter jack")); // alphabets only
        assertTrue(Question.isValidQuestion("12345")); // numbers only
        assertTrue(Question.isValidQuestion("peter the 2nd")); // alphanumeric characters
        assertTrue(Question.isValidQuestion("Capital Tan")); // with capital letters
        assertTrue(Question.isValidQuestion("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
