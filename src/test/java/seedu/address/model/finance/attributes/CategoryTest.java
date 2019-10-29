package seedu.address.model.finance.attributes;

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
    public void constructor_invalidCatName_throwsIllegalArgumentException() {
        String invalidCatName = "";
        assertThrows(IllegalArgumentException.class, () -> new Category(invalidCatName));
    }

    @Test
    public void isValidCatName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Category.isValidCatName(null));

        // invalid category names
        assertFalse(Category.isValidCatName("art supplies")); // 2 words
        assertFalse(Category.isValidCatName("@eroplane")); // special characters

        // valid category names
        assertTrue(Category.isValidCatName("art")); // single word
        assertTrue(Category.isValidCatName("suppl1es")); // alphanumeric

    }
}
