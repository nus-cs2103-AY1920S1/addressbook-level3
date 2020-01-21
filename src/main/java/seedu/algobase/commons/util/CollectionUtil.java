package seedu.algobase.commons.util;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

import javafx.collections.ObservableList;
import seedu.algobase.commons.core.index.Index;

/**
 * Utility methods related to Collections
 */
public class CollectionUtil {

    /** @see #requireAllNonNull(Collection) */
    public static void requireAllNonNull(Object... items) {
        requireNonNull(items);
        Stream.of(items).forEach(Objects::requireNonNull);
    }

    /**
     * Throws NullPointerException if {@code items} or any element of {@code items} is null.
     */
    public static void requireAllNonNull(Collection<?> items) {
        requireNonNull(items);
        items.forEach(Objects::requireNonNull);
    }

    /**
     * Returns true if {@code items} contain any elements that are non-null.
     */
    public static boolean isAnyNonNull(Object... items) {
        return items != null && Arrays.stream(items).anyMatch(Objects::nonNull);
    }

    public static boolean isArrayOfLength(Object[] array, int length) {
        return array.length == length;
    }

    /**
     * Checks if an observable list contains an index.
     *
     * @param index index to be checked.
     * @param items observable list of items.
     * @param <T> type of items that list contains.
     * @throws IndexOutOfBoundsException if list of items does not contain the index.
     */
    public static <T> boolean isWithinListRange(Index index, ObservableList<T> items) {
        int itemIndex = index.getZeroBased();
        if (itemIndex < 0 || itemIndex >= items.size()) {
            return false;
        } else {
            return true;
        }
    }
}
