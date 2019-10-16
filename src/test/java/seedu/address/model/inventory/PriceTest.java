package seedu.address.model.inventory;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PriceTest {
    @Test
    public void isValidName() {

        // invalid name
        assertFalse(Price.isValidName(-1)); // negative number
        assertFalse(Price.isValidName(-10)); // negative number
        assertFalse(Price.isValidName(-100)); // negative number

        // valid name
        assertTrue(Price.isValidName(1)); // small integer
        assertTrue(Price.isValidName(100)); // bigger integer
        assertTrue(Price.isValidName(50.50)); // decimal number
        assertTrue(Price.isValidName(0)); // 0 digit
    }
}
