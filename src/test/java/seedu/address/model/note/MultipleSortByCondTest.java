package seedu.address.model.note;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Comparator;

import org.junit.jupiter.api.Test;

class MultipleSortByCondTest {


    @Test
    void test_invalidSortByConditions_returnFalse() {
        String[] invalidSortConditions = new String[]{"xyz", "abc"};
        assertFalse(MultipleSortByCond.isValidSortByConditions(invalidSortConditions));
    }

    @Test
    void test_emptySortByConditions_returnFalse() {
        String[] invalidSortConditions = new String[]{};
        assertFalse(MultipleSortByCond.isValidSortByConditions(invalidSortConditions));
    }

    @Test
    void test_duplicateSortByConditions_returnFalse() {
        String[] invalidSortConditions = new String[]{"numofaccess, numofaccess"};
        assertFalse(MultipleSortByCond.isValidSortByConditions(invalidSortConditions));
    }

    @Test
    void test_validSortByConditions_returnTrue() {
        String[] validSortCondition0 = new String[]{"dateadded", "datemodified"};
        String[] validSortCondition1 = new String[]{"numofaccess", "datemodified"};
        String[] validSortCondition2 = new String[]{"numofacCess", "DATEMODIFIED"}; // uppercase characters
        String[] validSortCondition3 = new String[]{"numofacCess"}; // one string
        String[] validSortCondition4 = new String[]{"numofaccess", "datemodified", "dateadded"}; // 3 strings

        assertTrue(MultipleSortByCond.isValidSortByConditions(validSortCondition0));
        assertTrue(MultipleSortByCond.isValidSortByConditions(validSortCondition1));
        assertTrue(MultipleSortByCond.isValidSortByConditions(validSortCondition2));
        assertTrue(MultipleSortByCond.isValidSortByConditions(validSortCondition3));
        assertTrue(MultipleSortByCond.isValidSortByConditions(validSortCondition4));

    }

    @Test
    void testToString() {
    }
}