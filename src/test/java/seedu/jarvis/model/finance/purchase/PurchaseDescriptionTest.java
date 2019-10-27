package seedu.jarvis.model.finance.purchase;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PurchaseDescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PurchaseDescription(null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String invalidDescription = "";
        assertThrows(IllegalArgumentException.class, () -> new PurchaseDescription(invalidDescription));
    }

    @Test
    public void isValidDescription() {
        //null description
        assertThrows(NullPointerException.class, () -> PurchaseDescription.isValidDescription(null));

        // invalid name
        assertFalse(PurchaseDescription.isValidDescription("")); // empty string
        assertFalse(PurchaseDescription.isValidDescription(" ")); // spaces only
        assertFalse(PurchaseDescription.isValidDescription("^")); // only non-alphanumeric characters

        // valid name
        assertTrue(PurchaseDescription.isValidDescription("lunch")); // alphabets only
        assertTrue(PurchaseDescription.isValidDescription("iPhone 10")); // alphanumeric characters
    }

    @Test
    public void isEquals() {
        PurchaseDescription description1 = new PurchaseDescription("earphones");
        PurchaseDescription description2 = new PurchaseDescription("earphones");
        assertTrue(description1.equals(description2));
    }
}
