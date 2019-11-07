package seedu.address.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;

/**
 * Represents a graph implemented as an {@link Edge} list.
 */
public abstract class Graph {

    private static Graph emptyGraph = null;
    protected final List<Edge> edges;

    public Graph(Model model) {
        this.edges = new ArrayList<>();
        build(model);
    }

    /**
     * Returns an empty {@code Graph}.
     */
    public static Graph emptyGraph(Model model) {
        if (emptyGraph == null) {
            emptyGraph = new Graph(model) {
                @Override
                protected void build(Model model) {
                    // do nothing
                }

                @Override
                protected AutoCompleteResult process(String input) {
                    return new AutoCompleteResult(Collections.emptySortedSet(), "");
                }
            };
        }
        return emptyGraph;
    }

    /**
     * Builds the edge list of this graph.
     * @param model A database.
     */
    protected abstract void build(Model model);

    /**
     * Processes an input string by traversing the {@code Node}s of this graph.
     * @param input A user input string.
     * @return An {@code AutoCompleteResult} containing possible autocomplete values.
     */
    protected abstract AutoCompleteResult process(String input);

    /**
     * Iterates through the {@code Edge} list and returns edge weights for source node.
     * @param node A source node.
     * @return A list of edge weights (prefixes).
     */
    protected List<Prefix> getPrefixes(Node<?> node) {
        List<Prefix> prefixes = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.getSource().equals(node)) {
                prefixes.add(edge.getWeight());
            }
        }
        return prefixes;
    }

    /**
     * Attempts to return a neighbour node of input node by traversing an edge with given prefix.
     * @param currentNode A source node.
     * @param prefix An edge weight.
     * @return Empty optional if node does not have outgoing edge with given prefix, otherwise return neighbouring node.
     */
    protected Optional<Node<?>> traverse(Node<?> currentNode, Prefix prefix) {
        for (Edge edge : edges) {
            if (edge.getWeight().equals(prefix) && edge.getSource().equals(currentNode)) {
                return Optional.of(edge.getDestination());
            }
        }
        return Optional.empty();
    }

}
