package seedu.address.logic.autocomplete.edges;

import seedu.address.logic.autocomplete.nodes.Node;

/**
 * Represents an edge.
 * @param <T> The type of weight.
 * @param <U> The type of source node.
 * @param <V> The type of destination node.
 */
public interface Edge<T, U, V> {

    T getWeight();

    Node<U> getSource();

    Node<V> getDestination();

    boolean hasWeight(T weight);

    boolean hasSource(Node<?> node);

    boolean hasDestination(Node<?> node);

}
