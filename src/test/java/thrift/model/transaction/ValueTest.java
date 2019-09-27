package thrift.model.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static thrift.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

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
    public void constructor_invalidValueInvalidCurrency_throwsIllegalArgumentException() {
        String value = "";
        String currency = "";
        assertThrows(IllegalArgumentException.class, () -> new Value(value, currency));
    }

    @Test
    public void constructor_validValueInvalidCurrency_throwsIllegalArgumentException() {
        String value = "100";
        String currency = "";
        assertThrows(IllegalArgumentException.class, () -> new Value(value, currency));
    }

    @Test
    public void isValidValue() {
        // null value
        assertThrows(NullPointerException.class, () -> Value.isValidValue(null));

        // invalid value
        assertFalse(Value.isValidValue("")); // empty string
        assertFalse(Value.isValidValue(" ")); // spaces only
        assertFalse(Value.isValidValue("^")); // only non-numeric characters
        assertFalse(Value.isValidValue("1a")); // contains non-numeric characters
        assertFalse(Value.isValidValue("10.100")); // contains more than 2 decimal digits
        assertFalse(Value.isValidValue(".10")); // no decimal value

        // valid name
        assertTrue(Value.isValidValue("100")); // integer only
        assertTrue(Value.isValidValue("100.1")); // 1 decimal point
        assertTrue(Value.isValidValue("100.10")); // 2 decimal points
    }
}
