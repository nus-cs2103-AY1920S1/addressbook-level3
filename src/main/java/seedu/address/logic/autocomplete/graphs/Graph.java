package seedu.address.logic.autocomplete.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import seedu.address.logic.autocomplete.nodes.Node;

/**
 * Represents a directed graph implemented with an edge list.
 */
public abstract class Graph<V extends Node<?>> {

    protected final List<Edge<?, V>> edgeList;

    public Graph() {
        this.edgeList = new ArrayList<>();
    }

    @SafeVarargs
    public final void addEdges(Edge<?, V>... edges) {
        edgeList.addAll(Arrays.asList(edges));
    }

    /**
     * Iterates through the {@code Edge} list and returns edge weights for a given source node.
     * @param source A source node.
     * @return A list of edge weights.
     */
    public List<?> getWeights(Node<?> source) {
        List<Object> weights = new ArrayList<>();
        for (Edge<?, V> edge : edgeList) {
            if (edge.hasSource(source)) {
                weights.add(edge.getWeight());
            }
        }
        return weights;
    }

    /**
     * Attempts to return a destination node of a given source node by traversing an edge with given weight.
     * @param source A source node.
     * @param weight An edge weight.
     * @return Empty optional if node does not have outgoing edge with given weight, otherwise return destination node.
     */
    public Optional<V> traverse(V source, Object weight) {
        for (Edge<?, V> edge : edgeList) {
            if (edge.hasSource(source) && edge.hasWeight(weight)) {
                return Optional.of(edge.getDestination());
            }
        }
        return Optional.empty();
    }

}
