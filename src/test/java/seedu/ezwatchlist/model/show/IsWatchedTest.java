package seedu.ezwatchlist.model.show;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ezwatchlist.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class IsWatchedTest {

    @Test
    public void defaultConstructor() {
        assertFalse((new IsWatched()).value);
    }

    @Test
    public void constructorWithParameter() {
        assertTrue(new IsWatched("true").value);
    }

    @Test
    public void isValidIsWatched() {
        // null value
        assertThrows(NullPointerException.class, () -> IsWatched.isValidIsWatched(null));

        //invalid values
        assertFalse(() -> IsWatched.isValidIsWatched(" "));
        assertFalse(() -> IsWatched.isValidIsWatched("False"));
        assertFalse(() -> IsWatched.isValidIsWatched("TRUE"));

        //valid values
        assertTrue(() -> IsWatched.isValidIsWatched("true"));
        assertTrue(() -> IsWatched.isValidIsWatched("false"));
    }

    @Test
    public void hashCodeTest() {
        assertEquals(new IsWatched("true").hashCode(), 1);
        assertEquals(new IsWatched("true").getIsWatchedBoolean(), true);
    }
    public void getIsWatchedBoolean() {
        assertTrue(new IsWatched("true").getIsWatchedBoolean());
        assertFalse(new IsWatched("false").getIsWatchedBoolean());

    }
}
