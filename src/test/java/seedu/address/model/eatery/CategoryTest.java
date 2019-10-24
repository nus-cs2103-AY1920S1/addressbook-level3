package seedu.address.model.eatery;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void constructor_invalidCategoryName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Category(invalidName));
    }

    @Test
    public void constructor_validCategory() {
        String validName = "Japanese";
        Category validCategory = new Category(validName);
        assertEquals("Japanese", validCategory.getName());
    }

    @Test
    public void isValidCategory() {
        //null category
        assertThrows(NullPointerException.class, () -> Category.isValidCategory(null));

        //invalid category
        assertFalse(Category.isValidCategory(""));
        assertFalse(Category.isValidCategory(" "));
        assertFalse(Category.isValidCategory("0"));
        assertFalse(Category.isValidCategory("?"));
        assertFalse(Category.isValidCategory("Chinese?"));
        assertFalse(Category.isValidCategory("0 Chinese?"));

        //valid category
        assertTrue(Category.isValidCategory("Chinese"));
        assertTrue(Category.isValidCategory("chinese"));
        assertTrue(Category.isValidCategory("CHINESE"));
        assertTrue(Category.isValidCategory("Chinese Food"));
    }

    @Test
    public void equals() {
        Category japanese = new Category("Japanese");
        Category korean = new Category("Korean");

        assertTrue(japanese.equals(japanese));
        assertTrue(korean.equals(korean));
        assertTrue(japanese.equals(new Category("Japanese")));
        assertTrue(korean.equals(new Category("Korean")));

        assertFalse(japanese.equals(korean));
        assertFalse(korean.equals(japanese));
    }

    @Test
    public void create() {
        // test if it returns the same object
        Category thai = Category.create("Thai");
        Category secondThai = Category.create("Thai");
        assertEquals(thai, secondThai);

        // test if it returns a different object
        Category viet = Category.create("Vietnamese");
        assertFalse(thai == viet);
    }
}
