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
        assertThrows(NullPointerException.class, () -> Question.isValidName(null));

        // invalid name
        assertFalse(Question.isValidName("")); // empty string
        assertFalse(Question.isValidName(" ")); // spaces only
        assertFalse(Question.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Question.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Question.isValidName("peter jack")); // alphabets only
        assertTrue(Question.isValidName("12345")); // numbers only
        assertTrue(Question.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Question.isValidName("Capital Tan")); // with capital letters
        assertTrue(Question.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
