package seedu.guilttrip.model.entry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.guilttrip.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SortSequenceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SortSequence(null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String invalidSortSequence = "";
        assertThrows(IllegalArgumentException.class, () -> new SortSequence(invalidSortSequence));
    }

    @Test
    public void isValidDescription() {
        // null sortSequence
        assertThrows(NullPointerException.class, () -> SortSequence.isValidSortSequence(null));

        // invalid sortSequence
        assertFalse(SortSequence.isValidSortSequence("")); // empty string
        assertFalse(SortSequence.isValidSortSequence(" ")); // spaces only

        // valid sortSequence
        assertTrue(SortSequence.isValidSortSequence("ascending")); // ascending only
        assertTrue(SortSequence.isValidSortSequence("descending")); // descending only
        assertTrue(SortSequence.isValidSortSequence("AsCenDING")); // upper and lower case characters
    }
}
