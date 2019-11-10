package seedu.address.logic.autocomplete.edges;

import seedu.address.logic.autocomplete.nodes.AutoCompleteNode;
import seedu.address.logic.autocomplete.nodes.Node;

/**
 * Represents an {@code Edge} that has {@code AutoCompleteNode} as source and destination nodes.
 */
public class AutoCompleteEdge<T, U, V> implements Edge<T, U, V> {

    private final T weight;
    private final AutoCompleteNode<U> source;
    private final AutoCompleteNode<V> destination;

    public AutoCompleteEdge(T weight, AutoCompleteNode<U> source, AutoCompleteNode<V> destination) {
        this.weight = weight;
        this.source = source;
        this.destination = destination;
    }

    @Override
    public T getWeight() {
        return weight;
    }

    @Override
    public AutoCompleteNode<U> getSource() {
        return source;
    }

    @Override
    public AutoCompleteNode<V> getDestination() {
        return destination;
    }

    @Override
    public boolean hasWeight(T weight) {
        return this.weight.equals(weight);
    }

    @Override
    public boolean hasSource(Node<?> node) {
        return this.source.equals(node);
    }

    @Override
    public boolean hasDestination(Node<?> node) {
        return this.destination.equals(node);
    }

}
