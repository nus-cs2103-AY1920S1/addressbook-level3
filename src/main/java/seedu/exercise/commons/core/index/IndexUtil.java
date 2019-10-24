package seedu.exercise.commons.core.index;

import java.util.List;

/**
 * Utility methods for dealing with {@link Index}.
 */
public class IndexUtil {

    public static <T> boolean isIndexOutOfBounds(Index index, List<T> list) {
        return index.getZeroBased() >= list.size();
    }

    /**
     * Checks if any of the indexes in {@code indexList} is out of bounds of the given {@code list}.
     */
    public static <T> boolean areIndexesOutOfBounds(List<Index> indexList, List<T> list) {
        for (Index index : indexList) {
            if (isIndexOutOfBounds(index, list)) {
                return true;
            }
        }
        return false;
    }
}
