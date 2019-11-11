package seedu.sugarmummy.model.records;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sugarmummy.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class HeightTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Height(null));
    }

    @Test
    public void constructor_invalidHeight_throwsIllegalArgumentException() {
        String invalidHeight = "-1";
        assertThrows(IllegalArgumentException.class, () -> new Height(invalidHeight));
    }

    @Test
    public void isValidHeight() {
        // null name
        assertThrows(NullPointerException.class, () -> Height.isValidHeight(null));

        // invalid height
        assertFalse(Height.isValidHeight("")); // empty string
        assertFalse(Height.isValidHeight(" ")); // spaces only
        assertFalse(Height.isValidHeight("^")); // only non-alphanumeric characters
        assertFalse(Height.isValidHeight("peter")); // contains non-alphanumeric characters
        assertFalse(Height.isValidHeight("-2323")); // negative double

        // valid height
        assertTrue(Height.isValidHeight("0.12")); // ≤1 double
        assertTrue(Height.isValidHeight("2.99")); // ≥1 double
        assertFalse(Height.isValidHeight("2.9999999")); // ≥1 double
        assertFalse(Height.isValidHeight("10000")); // positive
        assertFalse(Height.isValidHeight("0"));
    }
}
