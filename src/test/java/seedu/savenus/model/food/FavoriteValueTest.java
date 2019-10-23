package seedu.savenus.model.food;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class FavoriteValueTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FavoriteValue(null));
    }

    @Test
    public void isValidValueTest() {
        assertTrue(FavoriteValue.isValidValue("0"));
        assertTrue(FavoriteValue.isValidValue("1"));
        assertTrue(!FavoriteValue.isValidValue("a"));
        assertTrue(!FavoriteValue.isValidValue("b"));
        assertTrue(!FavoriteValue.isValidValue(""));
    }

    @Test
    public void reverse_tests() {
        FavoriteValue zero = new FavoriteValue("0");
        FavoriteValue one = new FavoriteValue("1");
        zero.reverseValue();
        assertEquals(zero, one);
        zero.reverseValue();
        one.reverseValue();
        assertEquals(zero, one);
    }

    @Test
    public void compare_tests() {
        FavoriteValue zero = new FavoriteValue("0");
        FavoriteValue one = new FavoriteValue("1");
        assertNotEquals(zero.compareTo(one), 0);
        assertEquals(zero.compareTo(zero), 0);
        assertEquals(zero.compareTo(null), 1);
    }
}
