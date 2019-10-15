package seedu.savenus.model.food;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
        assertFalse(Price.isValidPrice("9312.1")); // too big amount

        // valid price numbers
        assertTrue(Price.isValidPrice("911.10")); // small amount
        assertTrue(Price.isValidPrice("4999.10")); // big amount
    }

    @Test
    public void isEmptyPrice() {
        assertFalse(Price.isValidPrice("")); // empty string
        assertFalse(Price.isValidPrice(" ")); // spaces only
        assertFalse(Price.isValidPrice("           ")); // tons of spaves
    }

    @Test
    public void get_field_test() {
        String sampleString = "5.00";
        assertEquals(new Price(sampleString).getField(), sampleString);
        assertNotEquals(new Price(sampleString).getField(), "");
    }

    @Test
    public void compareTests() {
        Price normalPrice = new Price("4.00");
        assertEquals(normalPrice.compareTo(null), 1);
        assertEquals(normalPrice.compareTo(normalPrice), 0);
    }
}
