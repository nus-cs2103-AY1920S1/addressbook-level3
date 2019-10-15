package io.xpire.commons.util;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
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
     * Converts a collection of objects into its string representation.
     * Optional mapper functions can be provided to mutate the string representation.
     *
     * @param items Any java object.
     * @param mappers Functions that do string processing.
     * @return A collection of strings.
     */
    @SafeVarargs
    public static Collection<String> stringifyCollection(Collection<?> items, Function<String, String>... mappers) {
        Function<String, String> finalMapper = Function.identity();
        for (Function<String, String> mapper : mappers) {
            finalMapper = finalMapper.andThen(mapper);
        }
        return items.stream()
                .map(Object::toString)
                .map(finalMapper)
                .collect(Collectors.toList());
    }
}
