package thrift.testutil;

import thrift.commons.core.index.Index;

/**
 * A utility class containing a list of {@code Index} objects to be used in tests.
 */
public class TypicalIndexes {
    public static final Index INDEX_FIRST_TRANSACTION = Index.fromOneBased(1);
    public static final Index INDEX_SECOND_TRANSACTION = Index.fromOneBased(2);
    public static final Index INDEX_THIRD_TRANSACTION = Index.fromOneBased(3);
}
