package seedu.address.model.food;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RestrictionsTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Restrictions(null));
    }

    @Test
    public void constructor_invalidRestrictions_throwsIllegalArgumentException() {
        String invalidRestrictions = "";
        assertThrows(IllegalArgumentException.class, () -> new Restrictions(invalidRestrictions));
    }

    @Test
    public void isValidRestrictions() {
        // null restrictions
        assertThrows(NullPointerException.class, () -> Restrictions.isValidRestrictions(null));

        // blank restrictions
        assertFalse(Restrictions.isValidRestrictions("")); // empty string
        assertFalse(Restrictions.isValidRestrictions(" ")); // spaces only

        // valid restrictions
        assertTrue(Restrictions.isValidRestrictions("PeterJack1examplecom"));
        assertTrue(Restrictions.isValidRestrictions("a@bc")); // minimal
        assertTrue(Restrictions.isValidRestrictions("test@localhost")); // alphabets only
        assertTrue(Restrictions.isValidRestrictions("!#$%&'*+/=?`{|}~^.-@example.org")); // special characters
        assertTrue(Restrictions.isValidRestrictions("123@145")); // numeric local part and domain name
    }
}
