package budgetbuddy.commons.util;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
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

    /**
     * Returns a list of all combinations possible for a given list of items.
     * @return A {@code List} of {@code List}s of the given items.
     */
    public static <T> List<List<T>> generateCombinations(List<T> items) {
        List<List<T>> results = new ArrayList<List<T>>();
        for (int i = 0; i < items.size(); i++) {
            results.addAll(generateCombinations(items, i));
        }
        return results;
    }

    /**
     * Returns a list of r-element combinations possible for a given list of items.
     * @param r The number of elements for each combination.
     * @return A {@code List} of size-{@code r} combinations of items from {@code items}.
     */
    public static <T> List<List<T>> generateCombinations(List<T> items, int r) {
        List<List<T>> results = new ArrayList<List<T>>();

        if (r == 0) {
            return results;
        }

        if (r == 1 || items.size() <= 1) {
            results.add(new ArrayList<T>(items));
            return results;
        }

        for (int i = 0; i < items.size(); i++) {
            final int currIndex = i;
            List<T> itemsAfterCurrIndex = new ArrayList<T>(items.subList(currIndex + 1, items.size()));
            results.addAll(generateCombinations(itemsAfterCurrIndex, r - 1)
                    .stream()
                    .peek(list -> list.add(0, items.get(currIndex)))
                    .collect(Collectors.toList()));
        }
        return results;
    }
}
