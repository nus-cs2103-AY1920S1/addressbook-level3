package seedu.address.model.finance.attributes;

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
    }
}
