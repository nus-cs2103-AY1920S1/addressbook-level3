package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import org.junit.jupiter.api.Test;
import seedu.address.model.transaction.Amount;

public class AmountTest {
    @Test
    public void validityCheckTest() {
        assertTrue(Amount.isValidAmount(12.34));
        assertTrue(Amount.isValidAmount(12.04));
        assertTrue(Amount.isValidAmount(109328402395748723242.04));
        assertFalse(Amount.isValidAmount(12.043));
        assertFalse(Amount.isValidAmount(12.0401));
    }
}
