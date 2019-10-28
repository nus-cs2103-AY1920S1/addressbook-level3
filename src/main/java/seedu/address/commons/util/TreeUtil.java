package seedu.address.commons.util;

import java.util.HashMap;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Stream;

public class TreeUtil {
    private TreeSet<IntegerPairUtil> treeSet;
    private HashMap<String, IntegerPairUtil> hashMap;

    public TreeUtil() {
        treeSet = new TreeSet<>();
        hashMap = new HashMap<>();
    }

    public void add(String value) {
        if(!value.isBlank()) {
            IntegerPairUtil storedPair = Optional.ofNullable(hashMap.get(value)).orElse(new IntegerPairUtil(0, value));
            treeSet.remove(storedPair);
            IntegerPairUtil newPair = new IntegerPairUtil(storedPair.getKey() + 1, storedPair.getValue());
            treeSet.add(newPair);
            hashMap.put(value, newPair);
        }
    }

    public boolean isEmpty() {
        return treeSet.isEmpty();
    }

    public Stream<IntegerPairUtil> stream() {
        return treeSet.descendingSet().stream();
    }
}
