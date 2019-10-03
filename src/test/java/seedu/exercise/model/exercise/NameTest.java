package seedu.exercise.model.exercise;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName("^32")); // only non-alphabetic characters
        assertFalse(Name.isValidName("Running*")); // contains non-alphabetic characters
        assertFalse(Name.isValidName("3432")); // only numeric characters


        // valid name
        assertTrue(Name.isValidName("running")); // alphabets only
        assertTrue(Name.isValidName("Running Fast")); // with capital letters
        assertTrue(Name.isValidName("Run Dive Walk Swim Triathlon")); // long names
    }
}
