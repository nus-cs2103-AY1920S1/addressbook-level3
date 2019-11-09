package seedu.address.commons.util;

import java.util.HashMap;
import java.util.Optional;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * {@code TreeUtil} used for generating statistics.
 * @param <V> type for {@code TreeUtil} that extends ferom {@link Comparable}
 */
public class TreeUtil<V extends Comparable> {
    private TreeSet<V> treeSet;
    private HashMap<String, V> hashMap;

    public TreeUtil() {
        treeSet = new TreeSet<>();
        hashMap = new HashMap<>();
    }

    /**
     * Add value to the {@code TreeUtil}
     * @param value value to be added to {@code TreeUtil}
     * @param defaultValueIfMissing default value to be added if missing
     * @param updateFunction function used to update value if it exists in the {@code TreeUtil}
     */
    public void add(String value, V defaultValueIfMissing, Function<V, V> updateFunction) {
        if (!value.isBlank()) {
            V prevValue = Optional.ofNullable(hashMap.get(value)).orElse(defaultValueIfMissing);
            treeSet.remove(prevValue);
            V newValue = updateFunction.apply(prevValue);
            treeSet.add(newValue);
            hashMap.put(value, newValue);
        }
    }

    public boolean isEmpty() {
        return treeSet.isEmpty();
    }

    public Stream<V> descendingStream() {
        return treeSet.descendingSet().stream();
    }

    public Stream<V> ascendingStream() {
        return treeSet.stream();
    }
}
