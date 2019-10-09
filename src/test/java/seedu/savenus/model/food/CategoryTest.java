package seedu.savenus.model.food;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Category(null));
    }

    @Test
    public void constructor_invalidCategory_throwsIllegalArgumentException() {
        String invalidCategory = "";
        assertThrows(IllegalArgumentException.class, () -> new Category(invalidCategory));
    }

    @Test
    public void isInvalidCategory() {
        // null email
        assertThrows(NullPointerException.class, () -> Category.isValidCategory(null));

        // blank email
        assertFalse(Category.isValidCategory("")); // empty string
        assertFalse(Category.isValidCategory(" ")); // spaces only

        // with unexpected characters
        assertFalse(Category.isValidCategory("@35^"));
    }

    @Test
    public void isValidCategory() {
        // valid email
        assertTrue(Category.isValidCategory("abc")); // short string
        assertTrue(Category.isValidCategory("Abracadabra")); // medium string
        assertTrue(Category.isValidCategory("Abracadabra Alakazam")); // long string
    }
}
