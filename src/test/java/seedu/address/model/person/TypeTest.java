package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TypeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Type(null));
    }

    @Test
    public void constructor_invalidType_throwsIllegalArgumentException() {
        String invalidType = "";
        assertThrows(IllegalArgumentException.class, () -> new Type(invalidType));
    }

    @Test
    public void isValidType() {
        // null type
        assertThrows(NullPointerException.class, () -> Type.isValidType(null));

        // invalid type
        assertFalse(Type.isValidType("")); // empty string
        assertFalse(Type.isValidType(" ")); // spaces only
        assertFalse(Type.isValidType("^")); // only non-alphanumeric characters
        assertFalse(Type.isValidType("peter*")); // contains non-alphanumeric characters

        assertFalse(Type.isValidType("peter jack")); // alphabets only
        assertFalse(Type.isValidType("12345")); // numbers only
        assertFalse(Type.isValidType("peter the 2nd")); // alphanumeric characters
        assertFalse(Type.isValidType("Capital Tan")); // with capital letters

        assertTrue(Type.isValidType("patient"));
        assertTrue(Type.isValidType("doctor"));
        assertTrue(Type.isValidType("donor"));
    }
}
