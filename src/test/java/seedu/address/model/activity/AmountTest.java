package seedu.address.model.activity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AmountTest {

    @Test
    public void constructor_invalidAmount_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Amount(-0.1));
    }

    @Test
    public void isValidAmount() {
        // invalid amounts
        assertFalse(Amount.isValidAmount(0));
        assertFalse(Amount.isValidAmount(-1.0));
        assertFalse(Amount.isValidAmount(-0.0001));

        // valid amounts
        assertTrue(Amount.isValidAmount(0.0001));
        assertTrue(Amount.isValidAmount(1.0));
        assertTrue(Amount.isValidAmount(9999));
    }
}
