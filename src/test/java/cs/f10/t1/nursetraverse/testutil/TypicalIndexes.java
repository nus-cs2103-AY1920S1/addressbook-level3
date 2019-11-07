package cs.f10.t1.nursetraverse.testutil;

import java.util.HashSet;
import java.util.Set;

import cs.f10.t1.nursetraverse.commons.core.index.Index;

/**
 * A utility class containing a list of {@code Index} objects to be used in tests.
 */
public class TypicalIndexes {
    public static final Index INDEX_FIRST_PATIENT = Index.fromOneBased(1);
    public static final Index INDEX_SECOND_PATIENT = Index.fromOneBased(2);
    public static final Index INDEX_THIRD_PATIENT = Index.fromOneBased(3);

    public static final Set<Index> getTypicalIndexSet() {
        Set<Index> indexSet = new HashSet<>();
        indexSet.add(INDEX_FIRST_PATIENT);
        indexSet.add(INDEX_SECOND_PATIENT);
        indexSet.add(INDEX_THIRD_PATIENT);

        return indexSet;
    }
}
