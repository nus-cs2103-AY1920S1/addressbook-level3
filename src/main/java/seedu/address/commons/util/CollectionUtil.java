package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
     * Throws {@link IllegalArgumentException} if any of the {@code collections} is empty.
     *
     * @see Collection#isEmpty()
     */
    public static void requireAllNotEmpty(Collection<?>... collections) {
        requireNonNull(collections);
        if (Stream.of(collections).anyMatch(Collection::isEmpty)) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Creates an unmodifiable {@link ObservableList} of the provided {@link List}.
     *
     * @param list The list of elements to wrap within an unmodifiable {@link ObservableList}.
     * @param <T> The type of elements in the {@code list}.
     * @return An unmodifiable {@link ObservableList} of the provided {@link List}.
     */
    public static <T> ObservableList<T> createUnmodifiableObservableList(final List<T> list) {
        return FXCollections.unmodifiableObservableList(FXCollections.observableList(list));
    }
}
