package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

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

    /**
     * Returns true if {@code item A} items are equal to {@code item B}.
     */
    public static boolean checkEqual(Collection<?> itemA, Collection<?> itemB) {
        Iterator itemBIterator = itemB.iterator();
        for (Object obj : itemA) {
            if (!itemBIterator.hasNext() || !obj.equals(itemBIterator.next())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a String representation of {@code items} with each element separated
     * by a newline.
     */
    public static <E> String collectionToString(Collection<E> items) {
        return collectionToString(items, false);
    }

    /**
     * Returns a String representation of {@code items} with each element separated
     * by a newline.
     */
    private static <E> String collectionToString(Collection<E> items, boolean showIndexes) {
        StringBuilder sb = new StringBuilder();
        int oneIndex = 1;
        for (E item : items) {
            if (showIndexes) {
                sb.append(oneIndex);
                sb.append(". ");
            }
            sb.append(item.toString());
            sb.append('\n');
            oneIndex++;
        }
        if (sb.length() != 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * Returns a String representation of {@code items} with each element preceded by its
     * one-based index number and separated by a newline.
     */
    public static <E> String collectionToStringShowingIndexes(Collection<E> items) {
        return collectionToString(items, true);
    }
}
