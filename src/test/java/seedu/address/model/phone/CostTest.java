package seedu.address.model.phone;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CostTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Cost(null));
    }

    @Test
    public void constructor_invalidCost_throwsIllegalArgumentException() {
        String invalidCost = "";
        assertThrows(IllegalArgumentException.class, () -> new Cost(invalidCost));
    }

    @Test
    void isValidCost() {
        // null cost
        assertThrows(NullPointerException.class, () -> Cost.isValidCost(null));

        // invalid cost
        assertFalse(Cost.isValidCost("")); // empty string
        assertFalse(Cost.isValidCost(" ")); // spaces only
        assertFalse(Cost.isValidCost("1")); // no leading $
        assertFalse(Cost.isValidCost("$-1.5")); // negative
        assertFalse(Cost.isValidCost("$4.501")); // more than 2 decimals
        assertFalse(Cost.isValidCost("$.12")); // no digit before decimal
        assertFalse(Cost.isValidCost("$0123")); // starts with 0 but is not $0 or $0.x or $0.xx
        assertFalse(Cost.isValidCost("$00123")); // more than one 0

        // valid cost
        assertTrue(Cost.isValidCost("$0")); // no decimals
        assertTrue(Cost.isValidCost("$1.5")); // 1 decimal
        assertTrue(Cost.isValidCost("$99.99")); // 2 decimals
        assertTrue(Cost.isValidCost("$1234567898765432100000000000000")); // long
        assertTrue(Cost.isValidCost("$1649"));
        assertTrue(Cost.isValidCost("$0"));
        assertTrue(Cost.isValidCost("$0.1"));
        assertTrue(Cost.isValidCost("$0.12"));
    }
}
