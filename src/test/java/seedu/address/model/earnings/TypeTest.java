package seedu.address.model.earnings;

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
        String invalidType = " ";
        assertThrows(IllegalArgumentException.class, () -> new Type(invalidType));
    }

    @Test
    public void constructor_invalidCombinationType_throwsIllegalArgumentException() {
        String invalidCombinationType = "lab + consultations";
        assertThrows(IllegalArgumentException.class, () -> new Type(invalidCombinationType));
    }

    @Test
    public void isValidType() {
        // null name
        assertThrows(NullPointerException.class, () -> Type.isValidType(null));

        // invalid name
        assertFalse(Type.isValidType("")); // empty string
        assertFalse(Type.isValidType(" ")); // spaces only
        assertFalse(Type.isValidType("discussion")); // invalid type characters

        // valid name
        assertTrue(Type.isValidType("lab")); // lab only
    }
}
