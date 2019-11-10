package seedu.address.model.usersettings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class MaxRenewsTest {
    private static final int TEST_MAX_RENEWS = 3;

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MaxRenews(null));
    }

    @Test
    public void constructor_invalidMaxRenews_throwsIllegalArgumentException() {
        String invalidMaxRenews = "";
        assertThrows(IllegalArgumentException.class, () -> new MaxRenews(invalidMaxRenews));
    }

    @Test
    public void isValidMaxRenews() {
        // null maxRenews
        assertThrows(NullPointerException.class, () -> MaxRenews.isValidMaxRenews(null));

        // invalid maxRenews
        assertFalse(MaxRenews.isValidMaxRenews("")); // empty string
        assertFalse(MaxRenews.isValidMaxRenews(" ")); // spaces only
        assertFalse(MaxRenews.isValidMaxRenews("^")); // only non-alphanumeric characters
        assertFalse(MaxRenews.isValidMaxRenews("hello*")); // contains non-alphanumeric characters
        assertFalse(MaxRenews.isValidMaxRenews("hello world")); // alphabets only
        assertFalse(MaxRenews.isValidMaxRenews("-1")); // negative integer
        assertFalse(MaxRenews.isValidMaxRenews("21")); // exceed maximum max renew count

        // valid maxRenews
        assertTrue(MaxRenews.isValidMaxRenews("10")); // numbers only
        assertTrue(MaxRenews.isValidMaxRenews("0")); // min value
        assertTrue(MaxRenews.isValidMaxRenews("20")); // max value
    }

    @Test
    public void toString_success() {
        MaxRenews mr = new MaxRenews(TEST_MAX_RENEWS);
        assertEquals(mr.toString(), String.format("%d", TEST_MAX_RENEWS));
    }

    @Test
    public void equals() {
        MaxRenews mr1 = new MaxRenews(TEST_MAX_RENEWS);
        MaxRenews mr2 = new MaxRenews(TEST_MAX_RENEWS);
        MaxRenews mr3 = new MaxRenews(TEST_MAX_RENEWS + 1);

        assertEquals(mr1, mr1);
        assertEquals(mr1, mr2);
        assertNotEquals(mr1, mr3);
    }

    @Test
    public void hashcode() {
        MaxRenews mr1 = new MaxRenews(TEST_MAX_RENEWS);
        MaxRenews mr2 = new MaxRenews(TEST_MAX_RENEWS);
        assertEquals(mr1.hashCode(), mr2.hashCode());
    }
}
