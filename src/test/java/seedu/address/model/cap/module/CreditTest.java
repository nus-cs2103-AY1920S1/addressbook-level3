package seedu.address.model.cap.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CreditTest {

    @Test
    public void isValidCredit() {

        // blank Credit
        assertFalse(Credit.isValidCredit("")); // empty string
        assertFalse(Credit.isValidCredit(" ")); // spaces only

        // invalid parts
        assertFalse(Credit.isValidCredit(0));
        assertFalse(Credit.isValidCredit(-1));
        assertFalse(Credit.isValidCredit(100));
        assertFalse(Credit.isValidCredit(999999));
        assertFalse(Credit.isValidCredit(-00));

        // missing parts
        assertFalse(Credit.isValidCredit("f")); // missing s
        assertFalse(Credit.isValidCredit("-")); // missing spaces
        assertFalse(Credit.isValidCredit("+")); // missing spaces

        // valid Credit
        assertTrue(Credit.isValidCredit(2));
        assertTrue(Credit.isValidCredit(3));
        assertTrue(Credit.isValidCredit(4));
        assertTrue(Credit.isValidCredit(5));
        assertTrue(Credit.isValidCredit(6));
        assertTrue(Credit.isValidCredit(7));
        assertTrue(Credit.isValidCredit(8));
        assertTrue(Credit.isValidCredit(9));
    }
}
