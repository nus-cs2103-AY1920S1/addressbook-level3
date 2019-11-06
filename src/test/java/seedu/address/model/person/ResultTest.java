package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ResultTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Result(null));
    }

    @Test
    public void constructor_invalidResult_throwsIllegalArgumentException() {
        String invalidResult = "full marks";
        assertThrows(IllegalArgumentException.class, () -> new Result(invalidResult));
    }

    @Test
    public void isValidResult() {
        assertThrows(NullPointerException.class, () -> Result.isValidResult(null));

        assertFalse(Result.isValidResult(""));
        assertFalse(Result.isValidResult(" "));
        assertFalse(Result.isValidResult("^"));
        assertFalse(Result.isValidResult("here"));

        assertTrue(Result.isValidResult("1"));
        assertTrue(Result.isValidResult("23"));
        assertTrue(Result.isValidResult("79"));
        assertTrue(Result.isValidResult("99"));
    }
}
