package seedu.address.logic;

/**
 * Represents a node in a {@link Graph}.
 */
public abstract class Node<V> {

    protected final V pointer;

    public Node(V pointer) {
        this.pointer = pointer;
    }

}
