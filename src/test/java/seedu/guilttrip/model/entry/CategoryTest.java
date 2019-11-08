package seedu.guilttrip.model.entry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.guilttrip.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    public void constructor_categoryNameNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Category(null, "Expense"));
        assertThrows(NullPointerException.class, () -> new Category(null, "Income"));

    }

    @Test
    public void constructor_invalidCategoryName_throwsIllegalArgumentException() {
        String invalidCategoryName = "";
        assertThrows(IllegalArgumentException.class, () -> new Category(invalidCategoryName, "Expense"));
        assertThrows(IllegalArgumentException.class, () -> new Category(invalidCategoryName, "Income"));
    }

    @Test
    public void isNotEmptyCategoryName() {
        // null name
        assertThrows(NullPointerException.class, () -> Category.isNotEmptyCategoryName(null));

        // invalid name
        assertFalse(Category.isNotEmptyCategoryName("")); // empty string
        assertFalse(Category.isNotEmptyCategoryName(" ")); // spaces only

        // valid name
        assertTrue(Category.isNotEmptyCategoryName("mala at deck")); // alphabets only
        assertTrue(Category.isNotEmptyCategoryName("6667")); // numbers only
        assertTrue(Description.isValidDescription("mala the 4th")); // alphanumeric characters
        assertTrue(Description.isValidDescription("MALAAAAAAA omg")); // with capital letters
        assertTrue(Description.isValidDescription("Deck mala Pines mala Home Mala Utown Mala 4")); // long names
    }

    @Test
    public void constructor_invalidCategoryType_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Category("mala at deck",
                "Budget"));
        assertThrows(IllegalArgumentException.class, () -> new Category("mala at deck",
                "invalidCategoryType"));
    }
}
