package seedu.address.ui;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Represents a node in a {@link Graph}.
 */
public class Node {

    private final List<?> backingList;

    public Node(List<?> backingList) {
        this.backingList = backingList;
    }

    public SortedSet<String> getValues() {
        SortedSet<String> values = new TreeSet<>();
        backingList.forEach(obj -> values.add(obj.toString()));
        return values;
    }

}
