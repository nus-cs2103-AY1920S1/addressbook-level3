package seedu.address.model.food;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String invalidDescription = "";
        assertThrows(IllegalArgumentException.class, () -> new Description(invalidDescription));
    }

    @Test
    public void isValidDescription() {
        // null description
        assertThrows(NullPointerException.class, () -> Description.isValidDescription(null));

        // blank description
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription(" ")); // spaces only

        // valid description
        assertTrue(Description.isValidDescription("PeterJack_1190@example.com"));
        assertTrue(Description.isValidDescription("a@bc")); // minimal
        assertTrue(Description.isValidDescription("test@localhost")); // alphabets only
        assertTrue(Description.isValidDescription("!#$%&'*+/=?`{|}~^.-@example.org")); // special characters local part
        assertTrue(Description.isValidDescription("123@145")); // numeric local part and domain name
        // mixture of alphanumeric and special characters
        assertTrue(Description.isValidDescription("a1+be!@example1.com"));
        assertTrue(Description.isValidDescription("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(Description.isValidDescription("if.you.dream.it_you.can.do.it@example.com")); // long local part
        assertTrue(Description.isValidDescription("if.you.dream.it_you.can.do.it@example.com")); // long local part
        assertTrue(Description.isValidDescription("if.you.dream.it_you.can.do.it@example.com")); // long local part
        assertTrue(Description.isValidDescription("if.you.dream.it_you.can.do.it@example.com")); // long local part
        assertTrue(Description.isValidDescription("if.you.dream.it_you.can.do.it@example.com")); // long local part
        assertTrue(Description.isValidDescription("if.you.dream.it_you.can.do.it@example.com")); // long local part
    }
}
