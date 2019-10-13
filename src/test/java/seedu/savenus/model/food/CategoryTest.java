package seedu.savenus.model.food;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
    public void isBlankCategory() {
        assertFalse(Category.isValidCategory("")); // empty string
        assertFalse(Category.isValidCategory(" ")); // spaces only
        assertFalse(Category.isValidCategory("             ")); // tons of spaces
    }

    @Test
    public void isInvalidCategory() {
        // null email
        assertThrows(NullPointerException.class, () -> Category.isValidCategory(null));

        // with unexpected characters
        assertFalse(Category.isValidCategory("@35^"));
        assertFalse(Category.isValidCategory("-------"));
    }

    @Test
    public void isValidCategory() {
        // valid email
        assertTrue(Category.isValidCategory("abc")); // short string
        assertTrue(Category.isValidCategory("Abracadabra")); // medium string
        assertTrue(Category.isValidCategory("Abracadabra Alakazam")); // long string
    }

    @Test
    public void get_field_test() {
        String sampleString = "Chinese";
        assertEquals(new Category(sampleString).getField(), sampleString);
        assertNotEquals(new Category(sampleString).getField(), "");
    }

    @Test
    public void compareTests() {
        Category normalCategory = new Category("Malay");
        assertEquals(normalCategory.compareTo(null), 1);
        assertEquals(normalCategory.compareTo(normalCategory), 0);
    }
}
