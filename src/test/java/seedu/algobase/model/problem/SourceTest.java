package seedu.algobase.model.problem;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.algobase.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class SourceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Source(null));
    }

    @Test
    public void constructor_invalidSource_throwsIllegalArgumentException() {
        String invalidSource = "";
        assertThrows(IllegalArgumentException.class, () -> new Source(invalidSource));
    }

    @Test
    public void isValidSource() {
        // null source
        assertThrows(NullPointerException.class, () -> Source.isValidSource(null));

        // invalid source
        assertFalse(Source.isValidSource("")); // empty string
        assertFalse(Source.isValidSource(" ")); // only spaces
        assertFalse(Source.isValidSource("^")); // non-whitelisted characters
        assertFalse(Source.isValidSource("Subset Sum")); // contains white space in between

        // valid source
        assertTrue(Source.isValidSource("LeetCode")); // only alphabets
        assertTrue(Source.isValidSource("123456789")); // only numbers
        assertTrue(Source.isValidSource("2ndMST")); // combination of alphanumeric characters
    }

}
