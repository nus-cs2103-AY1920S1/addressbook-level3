package seedu.address.model.quiz.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.quiz.person.Category;



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

        // invalid parts
        assertFalse(Category.isValidCategory("peterjack@-")); // invalid domain name
        assertFalse(Category.isValidCategory("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(Category.isValidCategory("peter jack@example.com")); // spaces in local part
        assertFalse(Category.isValidCategory("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(Category.isValidCategory(" peterjack@example.com")); // leading space
        assertFalse(Category.isValidCategory("peterjack@example.com ")); // trailing space
        assertFalse(Category.isValidCategory("peterjack@@example.com")); // double '@' symbol
        assertFalse(Category.isValidCategory("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(Category.isValidCategory("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(Category.isValidCategory("peterjack@.example.com")); // domain name starts with a period
        assertFalse(Category.isValidCategory("peterjack@example.com.")); // domain name ends with a period
        assertFalse(Category.isValidCategory("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(Category.isValidCategory("peterjack@example.com-")); // domain name ends with a hyphen

        // valid email
        assertTrue(Category.isValidCategory("PeterJack_1190@example.com"));
        assertTrue(Category.isValidCategory("a@bc")); // minimal
        assertTrue(Category.isValidCategory("test@localhost")); // alphabets only
        assertTrue(Category.isValidCategory("!#$%&'*+/=?`{|}~^.-@example.org")); // special characters local part
        assertTrue(Category.isValidCategory("123@145")); // numeric local part and domain name
        assertTrue(Category.isValidCategory("a1+be!@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(Category.isValidCategory("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(Category.isValidCategory("if.you.dream.it_you.can.do.it@example.com")); // long local part
    }
}
