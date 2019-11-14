package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
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
     * Returns true if all {{@code Optional} in {@code optionals }are not empty.
     *
     * Test needs to be created. Delete this comment once it is.
     */
    public static boolean isAllPresent(Optional... optionals) {
        requireNonNull(optionals);
        return Arrays.stream(optionals).allMatch(Optional::isPresent);
    }

    /**
     * Returns true if {@code items} contain any elements that are non-null.
     */
    public static boolean isAnyNonNull(Object... items) {
        return items != null && Arrays.stream(items).anyMatch(Objects::nonNull);
    }

    /**
     * Returns true if {@code optionals} has at least one {@code Optional} not being empty.
     *
     * Test needs to be created. Delete this comment once it is.
     */
    public static boolean isAnyPresent(Optional... optionals) {
        return optionals != null && Arrays.stream(optionals).anyMatch(Optional::isPresent);
    }
}
