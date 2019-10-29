package seedu.address.logic;

import seedu.address.logic.Graph;

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

    public abstract SortedSet<String> getValues();

}
