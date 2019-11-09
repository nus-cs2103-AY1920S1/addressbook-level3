package seedu.address.commons.util;

import java.util.HashMap;
import java.util.Optional;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Stream;

public class TreeUtil<V extends Comparable> {
    private TreeSet<V> treeSet;
    private HashMap<String, V> hashMap;

    public TreeUtil() {
        treeSet = new TreeSet<>();
        hashMap = new HashMap<>();
    }

    public <U> void add(String value, V defaultValueIfMissing, Function<V, V> function) {
        if(!value.isBlank()) {
            V prevValue = Optional.ofNullable(hashMap.get(value)).orElse(defaultValueIfMissing);
            treeSet.remove(prevValue);
            V newValue = function.apply(prevValue);
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
