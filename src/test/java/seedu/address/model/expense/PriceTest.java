package seedu.address.model.expense;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PriceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Price(null));
    }

    @Test
    public void constructor_invalidPrice_throwsIllegalArgumentException() {
        String invalidPrice = "";
        assertThrows(IllegalArgumentException.class, () -> new Price(invalidPrice));
    }

    @Test
    public void isValidPrice() {
        // null price
        assertThrows(NullPointerException.class, () -> Price.isValidPrice(null));

        // invalid prices
        assertFalse(Price.isValidPrice("")); // empty string
        assertFalse(Price.isValidPrice(" ")); // spaces only
        assertFalse(Price.isValidPrice("price")); // non-numeric
        assertFalse(Price.isValidPrice("9011p041")); // alphabets within digits
        assertFalse(Price.isValidPrice("9312 1534")); // spaces within digits
        assertFalse(Price.isValidPrice("9312,")); // end with comma
        assertFalse(Price.isValidPrice("9312.")); // end with period
        assertFalse(Price.isValidPrice(".9312")); // begin with period
        assertFalse(Price.isValidPrice(",9312")); // begin with comma
        assertFalse(Price.isValidPrice("9,3.12")); // multiple commas
        assertFalse(Price.isValidPrice("9.31.2")); // multiple periods
        assertFalse(Price.isValidPrice("9,.312")); // period and comma
        assertFalse(Price.isValidPrice("-9123")); // negative


        // valid prices
        assertTrue(Price.isValidPrice("0")); // price is 0
        assertTrue(Price.isValidPrice("91"));
        assertTrue(Price.isValidPrice("911")); // exactly 3 numbers
        assertTrue(Price.isValidPrice("93121534"));
        assertTrue(Price.isValidPrice("931,21534")); // comma
        assertTrue(Price.isValidPrice("931.21534")); // period
        assertTrue(Price.isValidPrice("93121534"));
        assertTrue(Price.isValidPrice("124293842033123")); // long prices
    }
}
