package seedu.address.ui;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a graph using an adjacency list.
 */
public abstract class Graph {

    private final String name;
    private final Node startNode;

    public String lastMatchStart;
    public String lastMatchEnd;

    public Graph(String name, Node startNode) {
        this.name = name;
        this.startNode = startNode;
    }

    public String getName() {
        return name;
    }

    public Node getStartNode() {
        return startNode;
    }

    public Node process(String input) {
        lastMatchStart = input;
        lastMatchEnd = "";
        Pattern prefixPattern = Pattern.compile(" [a-z]/");
        Matcher matcher = prefixPattern.matcher(input);
        Node currentNode = startNode;
        while (matcher.find()) {
            String prefix = matcher.group();
            System.out.println("Prefix: " + prefix);
            Optional<Node> nextNode = currentNode.traverse(prefix.substring(1));
            if (nextNode.isEmpty() || nextNode.get().getValues().isEmpty()) {
                System.out.println("Ending traversal.");
                break;
            } else {
                currentNode = nextNode.get();
            }
            lastMatchStart = input.substring(matcher.start());
            lastMatchEnd = input.substring(matcher.end());
        }
        return currentNode;
    }

    @Override
    public String toString() {
        return "Graph: " + this.name;
    }

}
