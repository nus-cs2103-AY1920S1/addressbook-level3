package seedu.address.model.currency;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

public class SymbolTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Symbol(null));
    }

    @Test
    public void constructor_invalidSymbol_throwsIllegalArgumentException() {
        String invalidSymbol = "";
        assertThrows(IllegalArgumentException.class, () -> new Symbol(invalidSymbol));
    }

    @Test
    public void isValidSymbol() {
        // null symbol
        assertThrows(NullPointerException.class, () -> Symbol.isValidSymbol(null));

        // invalid symbol
        assertFalse(Symbol.isValidSymbol("")); // empty string
        assertFalse(Symbol.isValidSymbol(" ")); // spaces only
        assertFalse(Symbol.isValidSymbol("#$&W")); // more than 3 characters
        assertFalse(Symbol.isValidSymbol("123")); // numbers only
        assertFalse(Symbol.isValidSymbol("1&")); // contains number
        assertFalse(Symbol.isValidSymbol("1 &")); // contains space in between characters
        assertFalse(Symbol.isValidSymbol("S+")); // contains +
        assertFalse(Symbol.isValidSymbol("S-")); // contains -
        assertFalse(Symbol.isValidSymbol(" &")); // contains space at beginning

        // valid symbol
        assertTrue(Symbol.isValidSymbol("tl")); // alphabets only
        assertTrue(Symbol.isValidSymbol("SG$")); // contains non-alphanumeric characters
        assertTrue(Symbol.isValidSymbol("₺")); // only non-alphanumeric characters
        assertTrue(Symbol.isValidSymbol("Rs")); // with capital letters
    }

}
