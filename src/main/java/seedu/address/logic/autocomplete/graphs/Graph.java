package seedu.address.logic.autocomplete.graphs;

import java.util.List;
import java.util.Optional;

import seedu.address.logic.autocomplete.nodes.Node;

/**
 * Represents a graph implemented with an edge list.
 */
public interface Graph<T> {
    /**
     * Iterates through the {@code Edge} list and returns edge weights for a given source node.
     * @param sourceNode A source node.
     * @return A list of edge weights.
     */
    List<T> getWeights(Node<?> sourceNode);

    /**
     * Attempts to return a destination node of a given source node by traversing an edge with given weight.
     * @param sourceNode A source node.
     * @param weight An edge weight.
     * @return Empty optional if node does not have outgoing edge with given weight, otherwise return destination node.
     */
    public Optional<? extends Node<?>> traverse(Node<?> sourceNode, T weight);

}
