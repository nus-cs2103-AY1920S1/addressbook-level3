package seedu.address.model.spending;

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
    public void isValidCost() {
        // null address
        assertThrows(NullPointerException.class, () -> Cost.isValidCost(null));

        // invalid addresses
        assertFalse(Cost.isValidCost("")); // empty string
        assertFalse(Cost.isValidCost(" ")); // spaces only

        // valid addresses
        assertTrue(Cost.isValidCost("100"));
        //assertTrue(Cost.isValidCost("-")); // one character
        //assertTrue(Cost.isValidCost("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}
