package seedu.address.logic.autocomplete.nodes;

import seedu.address.logic.autocomplete.graphs.Graph;

/**
 * Represents a node in a {@link Graph}.
 */
public abstract class Node<V> {

    protected final V pointer;

    public Node(V pointer) {
        this.pointer = pointer;
    }

}
