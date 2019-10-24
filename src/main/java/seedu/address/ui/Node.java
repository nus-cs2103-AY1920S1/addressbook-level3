package seedu.address.ui;

import seedu.address.logic.parser.Prefix;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Represents a node in {@link Graph}.
 */
public abstract class Node {

    private final Map<Prefix, Node> edges;
    private final SortedSet<String> values;

    public Node() {
        this.edges = new HashMap<>();
        this.values = new TreeSet<>();
    }

    public Node(Map<Prefix, Node> edges, SortedSet<String> values) {
        this.edges = edges;
        this.values = values;
    }

    public SortedSet<String> getValues() {
        return values;
    }

    public Map<Prefix, Node> getEdges() {
        return edges;
    }

    public Set<Prefix> getPrefixes() {
        return edges.keySet();
    }

    public Collection<Node> getNeighbours() {
        return edges.values();
    }

    public Optional<Node> traverse(Prefix prefix) {
        return Optional.ofNullable(edges.get(prefix));
    }

    public boolean isEnd() {
        return getNeighbours().isEmpty();
    }

}
