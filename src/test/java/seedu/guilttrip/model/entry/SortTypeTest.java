package seedu.guilttrip.model.entry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.guilttrip.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SortTypeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SortType(null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String invalidSortType = "";
        assertThrows(IllegalArgumentException.class, () -> new SortType(invalidSortType));
    }

    @Test
    public void isValidDescription() {
        // invalid SortType
        assertFalse(SortType.isValidSortType("")); // empty string
        assertFalse(SortType.isValidSortType(" ")); // spaces only

        // valid SortType
        assertTrue(SortType.isValidSortType("Category")); // ascending only
        assertTrue(SortType.isValidSortType("Time")); // ascending only
        assertTrue(SortType.isValidSortType("Description")); // ascending only
        assertTrue(SortType.isValidSortType("Tags")); // ascending only
        assertTrue(SortType.isValidSortType("Amount")); // ascending only
        assertTrue(SortType.isValidSortType("AmOuNt")); // ascending only
        assertTrue(SortType.isValidSortType("TAgS")); // ascending only
    }
}
