package seedu.jarvis.commons.util.andor;

import java.util.Collection;

/**
 * Represents a node with logical conjunction against its children.
 * @param <T> generic type
 */
public class AndNode<T> extends NoDataNode<T> {
    protected static final String STRING_REPRESENTATION = "all of";

    protected AndNode() {
        super();
    }

    /**
     * returns true only if all of elements in col exist in this node's children.
     *
     * @param col collection to check against
     * @return true if it follows the above condition
     */
    @Override
    protected boolean fulfills(Collection<T> col) {
        return children.stream().allMatch((child) -> child.fulfills(col));
    }

    @Override
    protected AndOrOperation type() {
        return AndOrOperation.AND;
    }

    @Override
    public String toString() {
        return STRING_REPRESENTATION;
    }
}
