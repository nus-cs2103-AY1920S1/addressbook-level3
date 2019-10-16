package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CopyUtil.deepCopy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
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
     * Returns a deep copy of the specified {@code ObservableList}. The copy is identical to the original except for
     * the listeners which will not be carried over. Any changes to the copied list or its elements will not affect
     * the original and vice versa.
     */
    public static <E> ObservableList<E> deepCopyOfObservableList(ObservableList<E> list) {
        List<E> listCopy = new ArrayList<>();
        for (E item : list) {
            listCopy.add(deepCopy(item));
        }
        return FXCollections.observableArrayList(listCopy);
    }

    /**
     * Returns a String representation of {@code items} with each element separated
     * by a newline.
     */
    public static <E> String collectionToString(Collection<E> items) {
        StringBuilder sb = new StringBuilder();
        for (E item : items) {
            sb.append(item.toString());
            sb.append('\n');
        }
        if (sb.length() != 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
