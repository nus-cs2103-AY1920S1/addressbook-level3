package seedu.jarvis.commons.util.andor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Represents an AndOrNode in the AndOrTree. The Node can be one of any three types
 *
 * 1. AndNode that represents logical conjunction
 * 2. OrNode that represents logical disjunction
 * 3. DataNode that simply stores some data
 *
 * @param <T> generic type
 */
public abstract class AndOrNode<T> {
    protected List<AndOrNode<T>> children;

    /**
     * Creates a new AndOrNode.
     */
    protected AndOrNode() {
        children = new ArrayList<>();
    }

    /**
     * Returns information stored in this node, {@code Optional.empty}
     * if there is no information.
     *
     * @return {@code Optional} info of type {@code T}
     */
    protected abstract Optional<T> getData();

    /**
     * Checks if this node fulfills its specified condition.
     *
     * @param col collection to check against
     * @return {@code true} if this node fulfills its condition.
     */
    protected abstract boolean fulfills(Collection<T> col);

    /**
     * Returns an {@code AndOrOperation enum} type of this node.
     *
     * @return an {@code AndOrOperation} type
     */
    protected abstract AndOrOperation type();

    /**
     * Returns a {@code String} containing the representation of this node.
     *
     * @return a {@code String}
     */
    public abstract String toString();

    /**
     * Inserts the given nodes into this node's children.
     *
     * @param nodes to insert
     */
    @SafeVarargs
    protected final void insert(AndOrNode<T>... nodes) {
        children.addAll(Arrays.asList(nodes));
    }

    /**
     * Inserts the given element into this node's children.
     *
     * @param data to insert
     */
    protected final void insert(T data) {
        AndOrNode<T> toInsert = createNode(data);
        insert(toInsert);
    }

    /**
     * Returns the children of this node.
     *
     * @return this node's children
     */
    protected List<AndOrNode<T>> getChildren() {
        return children;
    }

    /**
     * Creates new AndOrNode according to type.
     *
     * {@code type == "and"} creates an "And" node.
     * {@code type == "or"} creates an "Or" node.
     *
     * Any other type returns a DataNode with the info stored in data.
     *
     * @param data to store
     * @param type of the node
     * @param <T> generic type
     * @return a new {@code AndOrNode}
     */
    public static <T> AndOrNode<T> createNode(T data, String... type) {
        if (type.length == 0) {
            return new DataNode<>(data);
        }
        AndOrOperation nodeType = AndOrOperationMapper.ofType(type[0]);
        switch (nodeType) {
        case AND:
            return new AndNode<>();
        case OR:
            return new OrNode<>();
        default:
            return new DataNode<>(data);
        }
    }
}
