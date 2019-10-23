package seedu.address.ui;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Supplier;

/**
 * Represents a node in {@link Graph}.
 */
public class Node {

    private final Map<String, Node> edges;
    private final SortedSet<String> values;

    public Node(Supplier<Map<String, Node>> supplier, String... values) {
        this.edges = supplier.get();
        this.values = new TreeSet<>();
        this.values.addAll(Arrays.asList(values));
    }

    public SortedSet<String> getValues() {
        return values;
    }

    public Map<String, Node> getEdges() {
        return edges;
    }

    public Optional<Node> traverse(String prefix) {
        return Optional.ofNullable(edges.get(prefix));
    }

}
