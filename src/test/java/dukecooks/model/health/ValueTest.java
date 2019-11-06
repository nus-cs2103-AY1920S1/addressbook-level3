package dukecooks.model.health;

import static dukecooks.testutil.Assert.assertThrows;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dukecooks.model.health.components.Value;

public class ValueTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Value(null));
    }

    @Test
    public void constructor_invalidValue_throwsIllegalArgumentException() {
        String invalidValue = "";
        assertThrows(IllegalArgumentException.class, () -> new Value(invalidValue));
    }

    @Test
    public void isValidNumber() {
        // null value number
        assertThrows(NullPointerException.class, () -> Value.isValidNumber(null));

        // invalid value numbers
        assertFalse(Value.isValidNumber("")); // empty string
        assertFalse(Value.isValidNumber(" ")); // spaces only
        assertFalse(Value.isValidNumber("value")); // non-numeric
        assertFalse(Value.isValidNumber("9011p041")); // alphabets within digits
        assertFalse(Value.isValidNumber("9312 1534")); // spaces within digits

        // valid value numbers
        assertTrue(Value.isValidNumber("911")); // exactly 3 numbers
        assertTrue(Value.isValidNumber("93121534"));
        assertTrue(Value.isValidNumber("124293842033123")); // long value numbers
    }

    @Test
    public void testValueToString() {
        Value value = new Value("12");
        String expected = "12.0";
        assertEquals(value.toString(), expected);
    }
}
