package seedu.ichifund.model.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ichifund.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CategoryTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Category(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidDescription = "";
        assertThrows(IllegalArgumentException.class, () -> new Category(invalidDescription));
    }

    @Test
    public void isValidCategory() {
        // null category
        assertThrows(NullPointerException.class, () -> Category.isValidCategory(null));

        // invalid category
        assertFalse(Category.isValidCategory("")); // empty string
        assertFalse(Category.isValidCategory(" ")); // spaces only
        assertFalse(Category.isValidCategory("^")); // only non-alphanumeric characters
        assertFalse(Category.isValidCategory("food*")); // contains non-alphanumeric characters
        assertFalse(Category.isValidCategory("Breakfast with Tiffany because Sabrina disappearedd")); // too long

        // valid category
        assertTrue(Category.isValidCategory("food")); // alphabets only
        assertTrue(Category.isValidCategory("000")); // numbers only
        assertTrue(Category.isValidCategory("food1")); // alphanumeric characters
        assertTrue(Category.isValidCategory("Breakfast with Tiffany")); // with capital letters
        assertTrue(Category.isValidCategory("Breakfast with Tiffany because Sabrina disappeared")); // 50 characters
    }

    @Test
    public void testEquals() {
        // case-insensitivity
        assertTrue(new Category("food").equals(new Category("FOOD")));
    }
}
