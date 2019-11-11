package seedu.jarvis.commons.util.andor;

import java.util.Collection;

/**
 * Represents a node with logical disjunction against its children.
 * @param <T> generic type
 */
public class OrNode<T> extends NoDataNode<T> {
    protected static final String STRING_REPRESENTATION = "one of";

    protected OrNode() {
        super();
    }

    /**
     * returns true only if at least one of elements in col exist in this node's children.
     *
     * @param col collection to check against
     * @return true if it follows the above condition
     */
    @Override
    protected boolean fulfills(Collection<T> col) {
        return children.stream().anyMatch((child) -> child.fulfills(col));
    }

    @Override
    protected AndOrOperation type() {
        return AndOrOperation.OR;
    }

    @Override
    public String toString() {
        return STRING_REPRESENTATION;
    }
}
