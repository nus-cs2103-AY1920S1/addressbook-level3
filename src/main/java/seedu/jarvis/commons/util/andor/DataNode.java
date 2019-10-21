package seedu.jarvis.commons.util.andor;

import java.util.Collection;
import java.util.Optional;

/**
 * Represents a data node in the AndOrTree.
 * @param <T> generic type
 */
public class DataNode<T> extends AndOrNode<T> {
    private T data;

    protected DataNode(T data) {
        super();
        this.data = data;
    }

    /**
     * Returns an {@code Optional<T>} describing the data stored in this node.
     * @return an {@code Optional<T>}
     */
    @Override
    protected Optional<T> getData() {
        return Optional.of(data);
    }

    /**
     * Returns true if col contains the data stored in this node.
     * @param col collection to check against
     * @return true if it follows the above condition
     */
    @Override
    protected boolean fulfills(Collection<T> col) {
        return col.contains(data);
    }

    @Override
    protected AndOrOperation type() {
        return AndOrOperation.DATA;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
