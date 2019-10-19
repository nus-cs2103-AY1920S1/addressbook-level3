package seedu.exercise.model.resource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.exercise.model.property.Name;

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
        assertFalse(Name.isValidName("^32")); // contains non-alphanumeric characters
        assertFalse(Name.isValidName("Running*")); // contains non-alphanumeric characters


        // valid name
        assertTrue(Name.isValidName("running")); // alphabets only
        assertTrue(Name.isValidName("Running Fast")); // with capital letters
        assertTrue(Name.isValidName("Run Dive Walk Swim Triathlon")); // long names
        assertTrue(Name.isValidName("3432")); // numbers only
        assertTrue(Name.isValidName("Level 4")); // alphanumeric with spaces
        assertTrue(Name.isValidName("Level4")); // alphanumeric with no spaces
    }
}
