package dukecooks.model.health;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dukecooks.model.health.components.Type;
import dukecooks.testutil.Assert;

public class TypeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> Type.valueOf(null));
    }

    @Test
    public void constructor_invalidType_throwsIllegalArgumentException() {
        String invalidType = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> Type.valueOf(invalidType));
    }

    @Test
    public void isValidType() {
        // null type
        Assert.assertThrows(NullPointerException.class, () -> Type.isValidType(null));

        // invalid type
        assertFalse(Type.isValidType("")); // empty string
        assertFalse(Type.isValidType(" ")); // spaces only
        assertFalse(Type.isValidType("^")); // only non-alphanumeric characters
        assertFalse(Type.isValidType("peter*")); // contains non-alphanumeric characters

        // valid type
        assertTrue(Type.isValidType("Glucose")); // alphabets only
        assertTrue(Type.isValidType("glucose")); // small letters
        assertTrue(Type.isValidType("GLUCOSE")); // caps
    }

    @Test
    public void testTypeToString() {
        Type type = Type.Glucose;
        String expected = "Glucose";
        assertEquals(type.toString(), expected);
    }
}
