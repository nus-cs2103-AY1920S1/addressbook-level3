package mams.model.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import mams.testutil.Assert;

public class CreditsTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Credits(null));
    }

    @Test
    public void constructor_invalidCredits_throwsIllegalArgumentException() {
        String invalidCredits = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Credits(invalidCredits));
    }

    @Test
    public void isValidCredits() {
        // null credits number
        Assert.assertThrows(NullPointerException.class, () -> Credits.isValidCredits(null));

        // invalid credits numbers
        assertFalse(Credits.isValidCredits("")); // empty string
        assertFalse(Credits.isValidCredits(" ")); // spaces only


        // valid credits numbers
        assertTrue(Credits.isValidCredits("20")); // exactly 3 numbers
        assertTrue(Credits.isValidCredits("20"));
        assertTrue(Credits.isValidCredits("24")); // long credits numbers
    }
}
