package io.xpire.model.item.sort;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.xpire.testutil.Assert;

public class MethodOfSortingTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new MethodOfSorting(null));
    }

    @Test
    public void constructor_invalidMethodOfSorting_throwsIllegalArgumentException() {
        String invalidMethodOfSorting = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new MethodOfSorting(invalidMethodOfSorting));
    }

    @Test
    public void isValidMethodOfSorting() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> MethodOfSorting.isValidMethodOfSorting(null));

        // invalid method of sorting
        assertFalse(MethodOfSorting.isValidMethodOfSorting("")); // empty string
        assertFalse(MethodOfSorting.isValidMethodOfSorting(" ")); // spaces only
        assertFalse(MethodOfSorting.isValidMethodOfSorting("^")); // only name or date
        assertFalse(MethodOfSorting.isValidMethodOfSorting("3")); // only name or date

        // valid method of sorting, but wrong case
        assertFalse(MethodOfSorting.isValidMethodOfSorting("Name")); // should be "name"
        assertFalse(MethodOfSorting.isValidMethodOfSorting("Date")); // should be "date"

        // valid method of sorting, either name or date
        assertTrue(MethodOfSorting.isValidMethodOfSorting("name")); // name
        assertTrue(MethodOfSorting.isValidMethodOfSorting("date")); // date
    }
}
