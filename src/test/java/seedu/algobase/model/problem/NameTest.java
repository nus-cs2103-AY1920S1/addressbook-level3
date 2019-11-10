package seedu.algobase.model.problem;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.algobase.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class NameTest {

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
        assertFalse(Name.isValidName(" ")); // only spaces
        assertFalse(Name.isValidName("^")); // non-whitelisted characters
        assertFalse(Name.isValidName("*Subset Sum*")); // contains non-whitelisted characters

        // valid name
        assertTrue(Name.isValidName("VertexCover")); // only alphabets
        assertTrue(Name.isValidName("Dominating Set")); // alphabets with spaces
        assertTrue(Name.isValidName("123456789")); // only numbers
        assertTrue(Name.isValidName("2nd MST")); // combination of alphanumeric characters and spaces
    }

}
