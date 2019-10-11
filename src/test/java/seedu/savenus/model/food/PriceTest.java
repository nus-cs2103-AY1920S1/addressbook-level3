package seedu.savenus.model.food;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.testutil.Assert.assertThrows;

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
        // null price number
        assertThrows(NullPointerException.class, () -> Price.isValidPrice(null));

        // invalid price numbers
        assertFalse(Price.isValidPrice("price")); // non-numeric
        assertFalse(Price.isValidPrice("9011p041")); // alphabets within digits
        assertFalse(Price.isValidPrice("9312 1534")); // spaces within digits
        assertFalse(Price.isValidPrice("9312.1")); // digits with 1 decimal point

        // valid price numbers
        assertTrue(Price.isValidPrice("911.10")); // small amount
        assertTrue(Price.isValidPrice("93121534.10")); // big amount
        assertTrue(Price.isValidPrice("124293842033123.10")); // very big price numbers
    }

    @Test
    public void isEmptyPrice() {
        assertFalse(Price.isValidPrice("")); // empty string
        assertFalse(Price.isValidPrice(" ")); // spaces only
        assertFalse(Price.isValidPrice("           ")); // tons of spaves
    }
}
