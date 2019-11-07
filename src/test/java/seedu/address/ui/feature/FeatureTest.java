package seedu.address.ui.feature;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class FeatureTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Feature(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Feature(invalidName));
    }

    @Test
    public void isValidFeatureName() {
        // null name
        assertThrows(NullPointerException.class, () -> Feature.isValidFeatureName(null));

        // invalid name
        assertFalse(Feature.isValidFeatureName("")); // empty string
        assertFalse(Feature.isValidFeatureName("   ")); // spaces only
        assertFalse(Feature.isValidFeatureName("123")); // numbers only
        assertFalse(Feature.isValidFeatureName("@#$")); // special characters only
        assertFalse(Feature.isValidFeatureName("test")); // wrong string sequence

        // valid name
        assertTrue(Feature.isValidFeatureName("calendar"));
        assertTrue(Feature.isValidFeatureName("performance"));
        assertTrue(Feature.isValidFeatureName("attendance"));
    }

    @Test
    public void toStringTest() {
        assertEquals("calendar", new Feature("calendar").toString());
    }
}
