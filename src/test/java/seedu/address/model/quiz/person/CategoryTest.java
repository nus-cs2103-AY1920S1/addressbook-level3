package seedu.address.model.quiz.person;

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
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidEmail = "";
        assertThrows(IllegalArgumentException.class, () -> new Category(invalidEmail));
    }

    @Test
    public void isValidCategory() {
        // null email
        assertThrows(NullPointerException.class, () -> Category.isValidCategory(null));

        // blank email
        assertFalse(Category.isValidCategory("")); // empty string
        assertFalse(Category.isValidCategory(" ")); // spaces only

        // missing parts
        assertFalse(Category.isValidCategory("@example.com")); // missing local part
        assertFalse(Category.isValidCategory("peterjackexample.com")); // missing '@' symbol
        assertFalse(Category.isValidCategory("peterjack@")); // missing domain name

        // invalid category
        assertFalse(Category.isValidCategory("PeterJack_1190@example.com"));
        assertFalse(Category.isValidCategory("a@bc")); // minimal
        assertFalse(Category.isValidCategory("test@localhost")); // alphabets only
        assertFalse(Category.isValidCategory("!#$%&'*+/=?`{|}~^.-@example.org")); // special characters local part
        assertFalse(Category.isValidCategory("123@145")); // numeric local part and domain name
        assertFalse(Category.isValidCategory("a1+be!@example1.com")); // mixture of alphanumeric and special characters
        assertFalse(Category.isValidCategory("peter_jack@very-very-very-long-example.com")); // long domain name
        assertFalse(Category.isValidCategory("if.you.dream.it_you.can.do.it@example.com")); // long local part

        // valid category
        assertTrue(Category.isValidCategory("halloween"));
        assertTrue(Category.isValidCategory("halloween party"));
    }
}
