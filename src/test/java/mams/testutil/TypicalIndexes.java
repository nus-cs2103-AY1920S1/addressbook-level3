package mams.testutil;

import mams.commons.core.index.Index;

/**
 * A utility class containing a list of {@code Index} objects to be used in tests.
 */
public class TypicalIndexes {
    public static final Index INDEX_FIRST = Index.fromOneBased(1);
    public static final Index INDEX_SECOND = Index.fromOneBased(2);
    public static final Index INDEX_THIRD = Index.fromOneBased(3);
    public static final Index INDEX_FOURTH = Index.fromOneBased(4);
    // not-so-typical index. added for out-of-bounds checking in ViewCommand. can be used for other tests as well.
    public static final Index INDEX_MAX_INT = Index.fromZeroBased(Integer.MAX_VALUE);
}
