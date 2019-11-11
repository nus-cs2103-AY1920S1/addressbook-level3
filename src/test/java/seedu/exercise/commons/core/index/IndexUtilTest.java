package seedu.exercise.commons.core.index;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class IndexUtilTest {

    private static final Index ZERO_BASED_IN_BOUND = Index.fromZeroBased(0);
    private static final Index ZERO_BASED_OUT_BOUND = Index.fromZeroBased(2);
    private static final Index ONE_BASED_IN_BOUND = Index.fromOneBased(1);
    private static final Index ONE_BASED_OUT_BOUND = Index.fromOneBased(3);
    //List will have 2 items. Negative indexes are not tested due to nature of Index class rejecting negative values
    private static final List<String> TEST_LIST = new ArrayList<>();

    @BeforeAll
    public static void setUp() {
        TEST_LIST.add("1");
        TEST_LIST.add("2");
    }

    @Test
    public void isIndexOutOfBounds_zeroBasedIndexes_returnsCorrectBoolean() {
        assertFalse(IndexUtil.isIndexOutOfBounds(ZERO_BASED_IN_BOUND, TEST_LIST));
        assertTrue(IndexUtil.isIndexOutOfBounds(ZERO_BASED_OUT_BOUND, TEST_LIST));
    }

    @Test
    public void isIndexOutOfBounds_oneBasedIndexes_returnsCorrectBoolean() {
        assertFalse(IndexUtil.isIndexOutOfBounds(ONE_BASED_IN_BOUND, TEST_LIST));
        assertTrue(IndexUtil.isIndexOutOfBounds(ONE_BASED_OUT_BOUND, TEST_LIST));
    }

    @Test
    public void areIndexOutOfBounds_allIndexInBounds_returnsFalse() {
        List<Index> indexList = new ArrayList<>();
        indexList.add(ZERO_BASED_IN_BOUND);
        indexList.add(ONE_BASED_IN_BOUND);
        assertFalse(IndexUtil.areIndexesOutOfBounds(indexList, TEST_LIST));
    }

    @Test
    public void areIndexOutOfBounds_someIndexOutBounds_returnsTrue() {
        List<Index> indexList = new ArrayList<>();
        indexList.add(ZERO_BASED_OUT_BOUND);
        indexList.add(ZERO_BASED_IN_BOUND);
        indexList.add(ONE_BASED_IN_BOUND);
        assertTrue(IndexUtil.areIndexesOutOfBounds(indexList, TEST_LIST));
    }

    @Test
    public void isIndexOutOfBounds_nullArguments_throwsException() {
        assertThrows(NullPointerException.class, () -> IndexUtil.isIndexOutOfBounds(null, null));
    }

    @Test
    public void areIndexOutOfBounds_nullArguments_throwsException() {
        assertThrows(NullPointerException.class, () -> IndexUtil.areIndexesOutOfBounds(null, null));
    }
}
