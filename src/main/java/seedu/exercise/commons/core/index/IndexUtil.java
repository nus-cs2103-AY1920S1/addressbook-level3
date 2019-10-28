package seedu.exercise.commons.core.index;

import static seedu.exercise.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

/**
 * Utility methods for dealing with {@link Index}.
 */
public class IndexUtil {

    /**
     * Checks if the {@code index} is out of bounds of the {@code list}.
     * {@code index} is always positive.
     */
    public static <T> boolean isIndexOutOfBounds(Index index, List<T> list) {
        requireAllNonNull(index, list);
        return index.getZeroBased() >= list.size();
    }

    /**
     * Checks if any of the indexes in {@code indexList} is out of bounds of the given {@code list}.
     */
    public static <T> boolean areIndexesOutOfBounds(List<Index> indexList, List<T> list) {
        requireAllNonNull(indexList, list);
        for (Index index : indexList) {
            if (isIndexOutOfBounds(index, list)) {
                return true;
            }
        }
        return false;
    }
}
