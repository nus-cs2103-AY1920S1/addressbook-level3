package seedu.address.logic;

import java.util.Collections;
import java.util.List;
import java.util.SortedSet;

/**
 * Represents a node in a {@link Graph}.
 */
public abstract class Node<T> {

    protected final List<T> backingList;

    public Node(List<T> backingList) {
        this.backingList = backingList;
    }

    /**
     * Returns a {@code Node} that does not tracking anything.
     */
    public static Node emptyNode() {
        return new Node(Collections.emptyList()) {
            @Override
            public SortedSet<String> getValues() {
                return Collections.emptySortedSet();
            }
        };
    }

    public abstract SortedSet<String> getValues();

}
