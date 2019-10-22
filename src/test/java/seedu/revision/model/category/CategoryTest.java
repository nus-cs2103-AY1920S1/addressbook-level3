package seedu.revision.model.category;

import static seedu.revision.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Category(null));
    }

    @Test
    public void constructor_invalidCategoryName_throwsIllegalArgumentException() {
        String invalidCategoryName = "";
        assertThrows(IllegalArgumentException.class, () -> new Category(invalidCategoryName));
    }

    @Test
    public void isValidCategoryName() {
        // null Category name
        assertThrows(NullPointerException.class, () -> Category.isValidCategoryName(null));
    }

}
