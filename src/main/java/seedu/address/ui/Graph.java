package seedu.address.ui;

import seedu.address.logic.parser.Prefix;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a graph using an adjacency list.
 */
public abstract class Graph {

    private final String commandWord;
    private final Node startNode;
    private final Pattern prefixPattern = Pattern.compile(" [a-z]/");

    public String lastMatchEnd;

    public Graph(String commandWord, Node startNode) {
        this.commandWord = commandWord;
        this.startNode = startNode;
    }

    public String getCommandWord() {
        return commandWord;
    }

    public Node getStartNode() {
        return startNode;
    }

    public Node process(String input) {
        lastMatchEnd = "";
        Matcher matcher = prefixPattern.matcher(input);
        Node currentNode = startNode;
        while (matcher.find()) {
            String prefix = matcher.group().trim();
            Optional<Node> nextNode = currentNode.traverse(new Prefix(prefix));
            if (nextNode.isEmpty() || nextNode.get().isEnd()) {
                break;
            } else {
                currentNode = nextNode.get();
            }
            lastMatchEnd = input.substring(matcher.end());
        }
        return currentNode;
    }

    @Override
    public String toString() {
        return "Graph: " + this.commandWord;
    }

}
