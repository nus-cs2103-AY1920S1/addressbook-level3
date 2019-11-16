package seedu.ifridge.model.food;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ifridge.testutil.Assert.assertThrows;

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
        // null amount
        assertThrows(NullPointerException.class, () -> Amount.isValidAmount(null));

        // invalid amount
        assertFalse(Amount.isValidAmount("")); // empty string
        assertFalse(Amount.isValidAmount(" ")); // spaces only
        assertFalse(Amount.isValidAmount("^")); // invalid format
        assertFalse(Amount.isValidAmount("1000")); // numbers only, missing unit
        assertFalse(Amount.isValidAmount("kg")); // unit only
        assertFalse(Amount.isValidAmount("abcde")); // alphabets only
        assertFalse(Amount.isValidAmount("1000KG")); // invalid unit in capital letter
        assertFalse(Amount.isValidAmount("1000mole")); // invalid unit in capital letter
        assertFalse(Amount.isValidAmount("2e-10 mole")); // invalid format

        // negative amount
        assertFalse(Amount.isValidAmount("-0.05 g")); // invalid unit in capital letter
        assertFalse(Amount.isValidAmount("-1kg")); // invalid unit in capital letter
        assertFalse(Amount.isValidAmount("-10000ml")); // invalid unit in capital letter

        // additional zeroes
        assertTrue(Amount.isValidAmount("00.123 kg")); // leading zero in decimal
        assertTrue(Amount.isValidAmount("00000500 ml")); // leading zero in decimal
        assertTrue(Amount.isValidAmount("0.5000 g")); // trailing zero in decimal

        // additional whitespace
        assertTrue(Amount.isValidAmount("3.2      L")); // trailing zero in decimal

        // valid amount
        assertTrue(Amount.isValidAmount("1 lbs")); // valid value and unit with spacing
        assertTrue(Amount.isValidAmount("1 g")); // valid value and unit with spacing
        assertTrue(Amount.isValidAmount("1 kg")); // valid value and unit with spacing
        assertTrue(Amount.isValidAmount("1 oz")); // valid value and unit with spacing
        assertTrue(Amount.isValidAmount("1 L")); // valid value and unit with spacing
        assertTrue(Amount.isValidAmount("1 ml")); // valid value and unit with spacing
        assertTrue(Amount.isValidAmount("1 units")); // valid value and unit with spacing

        assertTrue(Amount.isValidAmount("0 lbs")); // valid value and unit with spacing
        assertTrue(Amount.isValidAmount("0 g")); // valid value and unit with spacing
        assertTrue(Amount.isValidAmount("0 kg")); // valid value and unit with spacing
        assertTrue(Amount.isValidAmount("0 oz")); // valid value and unit with spacing
        assertTrue(Amount.isValidAmount("0 L")); // valid value and unit with spacing
        assertTrue(Amount.isValidAmount("0 ml")); // valid value and unit with spacing
        assertTrue(Amount.isValidAmount("0 units")); // valid value and unit with spacing

        assertTrue(Amount.isValidAmount("1.32 lbs")); // valid value with decimal and unit with spacing
        assertTrue(Amount.isValidAmount("1.32 g")); // valid value with decimal and unit with spacing
        assertTrue(Amount.isValidAmount("1.32 kg")); // valid value with decimal and unit with spacing
        assertTrue(Amount.isValidAmount("1.32 oz")); // valid value with decimal and unit with spacing
        assertTrue(Amount.isValidAmount("1.32 L")); // valid value with decimal and unit with spacing
        assertTrue(Amount.isValidAmount("1.32 ml")); // valid value with decimal and unit with spacing
        assertTrue(Amount.isValidAmount("1.32 units")); // valid value with decimal and unit with spacing

        assertTrue(Amount.isValidAmount("1.32lbs")); // valid value with decimal and unit with no spacing
        assertTrue(Amount.isValidAmount("1.32g")); // valid value with decimal and unit with no spacing
        assertTrue(Amount.isValidAmount("1.32kg")); // valid value with decimal and unit with no spacing
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
        // valid unit
        assertEquals("ml", Amount.getUnit(new Amount("1.32ml"))); // without whitespace
        assertEquals("ml", Amount.getUnit(new Amount("1.32 ml"))); // with whitespace

        // invalid unit
        assertNotEquals("mL", Amount.getUnit(new Amount("1.32ml"))); // without whitespace
        assertNotEquals("mL", Amount.getUnit(new Amount("1.32   ml"))); // with whitespace
        assertNotEquals("kilogram", Amount.getUnit(new Amount("1.32kg"))); // expanding abbreviation
        assertNotEquals("kgs", Amount.getUnit(new Amount("1.32kg"))); // invalid unit
        assertNotEquals("unit", Amount.getUnit(new Amount("1 units"))); // unsupported
        assertNotEquals("unit", Amount.getUnit(new Amount("1units"))); // unsupported
    }
}
