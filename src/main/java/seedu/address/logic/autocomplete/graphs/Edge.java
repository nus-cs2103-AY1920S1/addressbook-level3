package seedu.address.logic.autocomplete.graphs;

import seedu.address.logic.autocomplete.nodes.Node;

/**
 * Represents an edge.
 * @param <T> The type of weight.
 * @param <V> The type of node.
 */
public class Edge<T, V extends Node<?>> {

    protected final T weight;
    protected final V source;
    protected final V destination;

    public Edge(T weight, V source, V destination) {
        this.weight = weight;
        this.source = source;
        this.destination = destination;
    }

    public T getWeight() {
        return weight;
    }

    public V getSource() {
        return source;
    }

    public V getDestination() {
        return destination;
    }

    public boolean hasWeight(Object weight) {
        return this.weight.equals(weight);
    }

    public boolean hasSource(Object source) {
        return this.source.equals(source);
    }

    public boolean hasDestination(Object destination) {
        return this.source.equals(destination);
    }

}
