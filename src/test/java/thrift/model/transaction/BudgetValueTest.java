package thrift.model.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static thrift.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class BudgetValueTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new BudgetValue(null));
    }

    @Test
    public void constructor_invalidValue_throwsIllegalArgumentException() {
        String invalidValue = "";
        assertThrows(IllegalArgumentException.class, () -> new BudgetValue(invalidValue));
    }

    @Test
    public void isValidValue() {
        // null value
        assertThrows(NullPointerException.class, () -> BudgetValue.isValidValue(null));

        // invalid values
        assertFalse(BudgetValue.isValidValue("")); // empty string
        assertFalse(BudgetValue.isValidValue(" ")); // space only
        assertFalse(BudgetValue.isValidValue("^")); // only non-numeric characters
        assertFalse(BudgetValue.isValidValue("1a")); // contains non-numeric characters
        assertFalse(BudgetValue.isValidValue("10.100")); // contains more than 2 decimal digits
        assertFalse(BudgetValue.isValidValue(".10")); // no whole number

        // valid value
        assertTrue(BudgetValue.isValidValue("100")); // integer only
        assertTrue(BudgetValue.isValidValue("100.1")); // 1 decimal point
        assertTrue(BudgetValue.isValidValue("100.10")); // 2 decimal points
        assertTrue(BudgetValue.isValidValue("0")); // 0 is valid for budget
    }
}
