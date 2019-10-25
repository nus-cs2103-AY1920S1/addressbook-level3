package seedu.address.ui;

import seedu.address.logic.parser.Prefix;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;

/**
 * Represents a node in {@link Graph}.
 */
public abstract class Node<T> {

    private final Map<Prefix, Node<T>> edges;
    private final ValueSet<T> values;

    public Node(List<T> values) {
        this.edges = new HashMap<>();
        this.values = new ValueSet<>(values);
    }

    public Node(Map<Prefix, Node<T>> edges, ValueSet<T> values) {
        this.edges = edges;
        this.values = values;
    }

    public SortedSet<String> getValues() {
        return values.getValues();
    }

    public Map<Prefix, Node<T>> getEdges() {
        return edges;
    }

    public Set<Prefix> getPrefixes() {
        return edges.keySet();
    }

    public Collection<Node<T>> getNeighbours() {
        return edges.values();
    }

    public Optional<Node<T>> traverse(Prefix prefix) {
        return Optional.ofNullable(edges.get(prefix));
    }

    public boolean isEnd() {
        return getNeighbours().isEmpty();
    }

}
