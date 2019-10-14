package seedu.address.model.groceryitem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AmountTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Amount(null));
    }

    @Test
    public void constructor_invalidAmount_throwsIllegalArgumentException() {
        String invalidAmount = "";
        assertThrows(IllegalArgumentException.class, () -> new Amount(invalidAmount));
    }

    @Test
    public void isValidAmount() {
        // null name
        assertThrows(NullPointerException.class, () -> Amount.isValidAmount(null));

        // invalid name
        assertFalse(Amount.isValidAmount("")); // empty string
        assertFalse(Amount.isValidAmount(" ")); // spaces only
        assertFalse(Amount.isValidAmount("^")); // invalid format
        assertFalse(Amount.isValidAmount("1000")); // numbers only, missing unit
        assertFalse(Amount.isValidAmount("kg")); // unit only
        assertFalse(Amount.isValidAmount("abcde")); // alphabets only
        assertFalse(Amount.isValidAmount("1000 KG")); // invalid unit in capital letter
        assertFalse(Amount.isValidAmount("1000 mole")); // invalid unit in capital letter

        //   "(lbs?|g|kgs?|oz?|L|ml?|units)";

        // valid name
        assertTrue(Amount.isValidAmount("1 lbs")); // valid value and unit with spacing
        assertTrue(Amount.isValidAmount("1 g")); // valid value and unit with spacing
        assertTrue(Amount.isValidAmount("1 kgs")); // valid value and unit with spacing
        assertTrue(Amount.isValidAmount("1 oz")); // valid value and unit with spacing
        assertTrue(Amount.isValidAmount("1 L")); // valid value and unit with spacing
        assertTrue(Amount.isValidAmount("1 ml")); // valid value and unit with spacing
        assertTrue(Amount.isValidAmount("1 units")); // valid value and unit with spacing

        assertTrue(Amount.isValidAmount("1.32 lbs")); // valid value with decimal and unit with spacing
        assertTrue(Amount.isValidAmount("1.32 g")); // valid value with decimal and unit with spacing
        assertTrue(Amount.isValidAmount("1.32 kgs")); // valid value with decimal and unit with spacing
        assertTrue(Amount.isValidAmount("1.32 oz")); // valid value with decimal and unit with spacing
        assertTrue(Amount.isValidAmount("1.32 L")); // valid value with decimal and unit with spacing
        assertTrue(Amount.isValidAmount("1.32 ml")); // valid value with decimal and unit with spacing
        assertTrue(Amount.isValidAmount("1.32 units")); // valid value with decimal and unit with spacing

        assertTrue(Amount.isValidAmount("1.32lbs")); // valid value with decimal and unit with no spacing
        assertTrue(Amount.isValidAmount("1.32g")); // valid value with decimal and unit with no spacing
        assertTrue(Amount.isValidAmount("1.32kgs")); // valid value with decimal and unit with no spacing
        assertTrue(Amount.isValidAmount("1.32oz")); // valid value with decimal and unit with no spacing
        assertTrue(Amount.isValidAmount("1.32L")); // valid value with decimal and unit with no spacing
        assertTrue(Amount.isValidAmount("1.32ml")); // valid value with decimal and unit with no spacing
        assertTrue(Amount.isValidAmount("1.32units")); // valid value with decimal and unit with no spacing
    }

    @Test
    public void getValue() {
        assertEquals(Float.valueOf("1.32"), Amount.getValue(new Amount("1.32ml")));
        assertEquals(Float.valueOf("1.32"), Amount.getValue(new Amount("1.32 ml")));
    }

    @Test
    public void getUnit() {
        assertEquals("ml", Amount.getUnit(new Amount("1.32ml")));
        assertEquals("ml", Amount.getUnit(new Amount("1.32 ml")));
    }
}
