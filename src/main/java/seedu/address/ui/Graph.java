package seedu.address.ui;

import seedu.address.logic.parser.Prefix;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a graph implemented as an {@link Edge} list.
 */
public class Graph<T> {

    private static final Pattern prefixPattern = Pattern.compile(" [a-z]/");

    private final Node<T> startingNode;
    private final List<Edge<T>> edges;

    private Node<T> currentNode;
    public String wordToCompare;

    public Graph(Node<T> startingNode, List<Edge<T>> edges) {
        this.startingNode = startingNode;
        this.edges = edges;
    }

    public SortedSet<String> process(String input) {
        resetCurrentNode();
        SortedSet<String> values = new TreeSet<>();
        Matcher matcher = prefixPattern.matcher(input);
        while (matcher.find()) {
            Prefix prefix = new Prefix(matcher.group().trim());
            traverse(prefix);
            wordToCompare = input.substring(matcher.end());
        }
        if (input.endsWith(" ")) {
            // prefixes
            edges.forEach(edge -> {
                if (edge.getSource().equals(currentNode)) {
                    values.add(edge.getWeight().toString());
                }
            });
            wordToCompare = "";
        } else {
            values.addAll(currentNode.getValues());
        }
        return values;
    }

    private void resetCurrentNode() {
        currentNode = startingNode;
    }

    private void traverse(Prefix prefix) {
        for (Edge<T> edge : edges) {
            if (edge.getWeight().equals(prefix) && edge.getSource().equals(currentNode)) {
                currentNode = edge.getDestination();
                break;
            }
        }
    }

}
