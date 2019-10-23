package seedu.address.model.category;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Category(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Category(invalidTagName));
    }

    @Test
    public void getCategoryName() {
        Category validCategory = new Category("food");

        assertTrue(validCategory.getCategoryName().equals("food"));
        assertFalse(validCategory.getCategoryName().equals("drink"));
    }

    @Test
    public void isValidCategoryName() {
        // null category name
        assertThrows(NullPointerException.class, () -> Category.isValidCategoryName(null));
    }

    @Test
    public void equals() {
        Category firstCategory = new Category("club");
        Category secondCategory = new Category("drink");

        // same object -> returns true
        assertTrue(firstCategory.equals(firstCategory));

        // same values -> returns true
        assertTrue(firstCategory.equals(new Category("club")));

        // different types -> returns false
        assertFalse(firstCategory.equals(1));

        // null -> returns false
        assertFalse(firstCategory.equals(null));

        // different Category -> returns false
        assertFalse(firstCategory.equals(secondCategory));
    }

    @Test
    public void testToString() {
        Category validCategory = new Category("food");

        assertTrue(validCategory.toString().equals("[food]"));
        assertFalse(validCategory.toString().equals("[drink]"));
    }

}
