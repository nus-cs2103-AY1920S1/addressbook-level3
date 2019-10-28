package seedu.exercise.commons.util;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
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
     * Appends two list together into a new list.
     */
    public static <T> List<T> append(List<T> first, List<T> second) {
        requireAllNonNull(first, second);
        List<T> result = new ArrayList<>(first);
        result.addAll(second);
        return result;
    }

    /**
     * Checks if both lists are empty. Only returns true if both are empty.
     */
    public static <T> boolean areListsEmpty(List<T> first, List<T> second) {
        requireAllNonNull(first, second);
        return first.isEmpty() && second.isEmpty();
    }
}
