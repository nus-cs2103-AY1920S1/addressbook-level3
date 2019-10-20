package budgetbuddy.commons.util;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
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
     * Returns true if {@code items} contain any duplicates.
     */
    //@@author kenneth-fung-reused
    //Reused from https://stackoverflow.com/a/600319 with minor modifications
    public static <T> boolean hasDuplicates(Collection<T> items) {
        Set<T> hashSet = new HashSet<T>();
        for (T item : items) {
            if (!hashSet.add(item)) {
                return true;
            }
        }
        return false;
    }
    //@@author
}
